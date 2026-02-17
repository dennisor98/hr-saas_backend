package open.soft.hr_backend.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetOTPReq {
  @NotNull
    private String mobileNumber;
}
