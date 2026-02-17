package open.soft.hr_backend.domain;

import lombok.Data;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Embeddable
public class EmployeeTimeOffPolicyId implements Serializable {
    private UUID employeeId;
    private UUID policyId;
    private LocalDate effectiveDate;
}