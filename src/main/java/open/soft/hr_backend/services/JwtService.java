package open.soft.hr_backend.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import open.soft.hr_backend.JwtType;
import open.soft.hr_backend.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Service
@Slf4j
public class JwtService {
    @Value("${JWT_SECRET}")
    String jwtSecret;

    @Value("${JWT_EXPIRY_TIME:5184000}")
    Long jwtExpiryTime;

    @Value("${REFRESH_JWT_EXPIRY_TIME:604800}")
    Long jwtRefreshExpiryTime;

    byte[] decodedKey = null;
    SecretKeySpec secretKey = null;

    @PostConstruct()
    void init() {
        decodedKey = Base64.getDecoder().decode(jwtSecret);
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HMACSHA256");
    }

    public String generateToken(User user) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("token_type", JwtType.ACCESS_TOKEN.getToken());
            claims.put("tenant_id",null);
            log.info(jwtSecret);
            return Jwts.builder().setClaims(claims).setSubject(user.getId().toString()).setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))// 1 hour validity
                    .setId(UUID.randomUUID().toString())

                    .signWith(secretKey, SignatureAlgorithm.HS256).compact();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String getRefreshToken(User user) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("token_type", JwtType.REFRESH_TOKEN.getToken());
            claims.put("tenant_id",null);
            log.info(jwtSecret);
            return Jwts.builder().setClaims(claims).setSubject(user.getId().toString()).setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))// 1 hour validity
                    .setId(UUID.randomUUID().toString())

                    .signWith(secretKey, SignatureAlgorithm.HS256).compact();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
