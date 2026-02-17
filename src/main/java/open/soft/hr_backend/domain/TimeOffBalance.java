package open.soft.hr_backend.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "time_off_balances", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employee_id", "policy_id", "year"})
})
public class TimeOffBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, columnDefinition = "UUID")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false, columnDefinition = "UUID")
    private TimeOffPolicy policy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, columnDefinition = "UUID")
    private Tenant tenant;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "accrued_hours", precision = 7, scale = 2)
    private BigDecimal accruedHours = BigDecimal.ZERO;

    @Column(name = "taken_hours", precision = 7, scale = 2)
    private BigDecimal takenHours = BigDecimal.ZERO;

    @Column(name = "scheduled_hours", precision = 7, scale = 2)
    private BigDecimal scheduledHours = BigDecimal.ZERO;

    // This is a generated column, so we don't insert/update it
    @Column(name = "remaining_hours", insertable = false, updatable = false, precision = 7, scale = 2)
    private BigDecimal remainingHours;

    @Column(name = "carried_over_hours", precision = 7, scale = 2)
    private BigDecimal carriedOverHours = BigDecimal.ZERO;

    @Column(name = "last_calculated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime lastCalculatedAt;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
        lastCalculatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
        lastCalculatedAt = OffsetDateTime.now();
    }
}