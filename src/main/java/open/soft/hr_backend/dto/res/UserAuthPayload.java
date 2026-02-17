package open.soft.hr_backend.dto.res;


import lombok.Data;

@Data
public class UserAuthPayload {
    private String tenant_id;
    private String user_id;
    private String first_name;
    private String lastName;
    private String email;
    private String phone;
}
