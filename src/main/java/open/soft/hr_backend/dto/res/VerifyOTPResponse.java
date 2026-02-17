package open.soft.hr_backend.dto.res;

import lombok.Data;

@Data
public class VerifyOTPResponse {
    private String message;
    private String access_token;
    private String refresh_token;
    UserAuthPayload user;
}
