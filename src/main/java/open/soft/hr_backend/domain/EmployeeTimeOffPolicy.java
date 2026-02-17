package open.soft.hr_backend.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "employee_time_off_policies")
public class EmployeeTimeOffPolicy {

    @EmbeddedId
    private EmployeeTimeOffPolicyId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TimeOffPolicy policy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, columnDefinition = "UUID")
    private Tenant tenant;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "custom_accrual_rate", precision = 5, scale = 2)
    private BigDecimal customAccrualRate;
}