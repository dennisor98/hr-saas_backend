package open.soft.hr_backend.dto.req;

import lombok.Data;
import open.soft.hr_backend.domain.User;

@Data
public class OTPRedisDTO {
    private String hash;
    private String code;
    private User user;
}
