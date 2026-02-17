package open.soft.hr_backend.dto.res;

import lombok.Data;

@Data
public class GetOTPRes {
    private String message;
    private String hash;
}
