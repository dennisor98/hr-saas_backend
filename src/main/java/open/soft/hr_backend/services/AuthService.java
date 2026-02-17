package open.soft.hr_backend.services;

import com.google.gson.Gson;
import open.soft.hr_backend.domain.User;
import open.soft.hr_backend.dto.req.GetOTPReq;
import open.soft.hr_backend.dto.req.OTPRedisDTO;
import open.soft.hr_backend.dto.req.VerifyOTPReq;
import open.soft.hr_backend.dto.res.GetOTPRes;
import open.soft.hr_backend.dto.res.UserAuthPayload;
import open.soft.hr_backend.dto.res.VerifyOTPResponse;
import open.soft.hr_backend.repository.UserRepository;
import open.soft.hr_backend.utility.AdvancedUniqueKeyGenerator;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

import java.time.Duration;
import java.util.Optional;

public class AuthService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private Gson gson;
    public ResponseEntity<GetOTPRes> getOTP(GetOTPReq req) {
        Optional<User> userOptional = userRepository.findByPhone(req.getMobileNumber());

        if(userOptional.isEmpty()){
            GetOTPRes res =  new GetOTPRes();
            res.setHash(null);
            res.setMessage("Account not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(res);
        }

        User user =  userOptional.get();

        // invoke sms sender service
        String code = AdvancedUniqueKeyGenerator.generateOTP(4);
        String hash = AdvancedUniqueKeyGenerator.generateOTPHash();

        OTPRedisDTO otpRes =  new OTPRedisDTO();
        otpRes.setUser(user);
        otpRes.setCode(code);

        String redisStr =  gson.toJson(otpRes);
        //cache otp code and user object with hash as the key
        redisTemplate.opsForValue().set("verif_otp:"+hash,redisStr, Duration.ofMinutes(5));

        GetOTPRes res  = new GetOTPRes();
        res.setMessage("OTP sent to "+req.getMobileNumber());
        res.setHash(hash);
     return ResponseEntity.status(HttpStatus.OK).body(res);
 }


 public ResponseEntity<VerifyOTPResponse> verifyOTP(VerifyOTPReq req){
        String otpHash = redisTemplate.opsForValue().get("verif_otp:"+req.getHash());
        if (otpHash == null){
            VerifyOTPResponse res = new VerifyOTPResponse();
            res.setMessage("Invalid OTP code");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }

     OTPRedisDTO keyObj = gson.fromJson(otpHash,OTPRedisDTO.class);
        String code  = keyObj.getCode();
        if(!req.getCode().equalsIgnoreCase(code)){
            VerifyOTPResponse res = new VerifyOTPResponse();
            res.setMessage("Invalid OTP code");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        User user =  keyObj.getUser();
        String access_token =  jwtService.generateToken(user);
        String refresh_token = jwtService.getRefreshToken(user);

     UserAuthPayload userAuth =  new UserAuthPayload();
     userAuth.setEmail(user.getEmail());
     userAuth.setUser_id(user.getId());
     userAuth.setPhone(user.getPhone());
     userAuth.setTenant_id(user.getTenant().getId());
     userAuth.setFirst_name(user.getFirstName());
     userAuth.setLastName(user.getLastName());

     VerifyOTPResponse res =  new VerifyOTPResponse();
     res.setMessage("OTP verification success");
     res.setUser(userAuth);
     res.setAccess_token(access_token);
     res.setRefresh_token(refresh_token);
        return ResponseEntity.status(HttpStatus.OK).body(res);
 }
}
