package open.soft.hr_backend.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "employment_history")
public class EmploymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, columnDefinition = "UUID")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, columnDefinition = "UUID")
    private Tenant tenant;

    // Previous state
    @Column(name = "position_id", columnDefinition = "UUID")
    private UUID positionId;

    @Column(name = "department_id", columnDefinition = "UUID")
    private UUID departmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Column(name = "job_title", length = 255)
    private String jobTitle;

    @Column(name = "employment_type", length = 50)
    private String employmentType;

    @Column(name = "pay_type", length = 50)
    private String payType;

    @Column(name = "pay_rate", precision = 15, scale = 2)
    private BigDecimal payRate;

    @Column(name = "pay_period", length = 50)
    private String payPeriod;

    @Column(name = "currency", length = 3)
    private String currency;

    // Change details
    @Column(name = "change_type", length = 50)
    private String changeType;

    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}
