package open.soft.hr_backend.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "time_off_policies")
public class TimeOffPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, columnDefinition = "UUID")
    private Tenant tenant;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "description")
    private String description;

    // Accrual rules
    @Column(name = "accrual_rate", precision = 5, scale = 2)
    private BigDecimal accrualRate;

    @Column(name = "accrual_period", length = 50)
    private String accrualPeriod;

    @Column(name = "max_accrual", precision = 7, scale = 2)
    private BigDecimal maxAccrual;

    @Column(name = "allow_negative_balance")
    private Boolean allowNegativeBalance = false;

    @Column(name = "max_negative_hours", precision = 7, scale = 2)
    private BigDecimal maxNegativeHours;

    // Request rules
    @Column(name = "min_days_before_request")
    private Integer minDaysBeforeRequest;

    @Column(name = "max_days_per_request")
    private Integer maxDaysPerRequest;

    @Column(name = "requires_approval")
    private Boolean requiresApproval = true;

    @Column(name = "approver_roles", columnDefinition = "JSONB")
    private String approverRoles;

    // Carry over rules
    @Column(name = "carry_over_limit", precision = 7, scale = 2)
    private BigDecimal carryOverLimit;

    @Column(name = "carry_over_expires_months")
    private Integer carryOverExpiresMonths;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}