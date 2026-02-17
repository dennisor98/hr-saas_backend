package open.soft.hr_backend.dto.req;

import lombok.Data;

@Data
public class VerifyOTPReq {
    private String hash;
    private String code;
}
