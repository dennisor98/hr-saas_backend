package open.soft.hr_backend.domain;

import lombok.Data;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class UserRoleId implements Serializable {
    private UUID userId;
    private UUID roleId;
}