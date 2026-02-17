package open.soft.hr_backend.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "positions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tenant_id", "code"})
})
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, columnDefinition = "UUID")
    private Tenant tenant;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "job_family", length = 100)
    private String jobFamily;

    @Column(name = "job_level", length = 50)
    private String jobLevel;

    @Column(name = "description")
    private String description;

    @Column(name = "requirements")
    private String requirements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reports_to_position_id")
    private Position reportsToPosition;

    @Column(name = "location_id", columnDefinition = "UUID")
    private UUID locationId;

    // Compensation band
    @Column(name = "salary_min", precision = 15, scale = 2)
    private BigDecimal salaryMin;

    @Column(name = "salary_max", precision = 15, scale = 2)
    private BigDecimal salaryMax;

    @Column(name = "hourly_min", precision = 10, scale = 2)
    private BigDecimal hourlyMin;

    @Column(name = "hourly_max", precision = 10, scale = 2)
    private BigDecimal hourlyMax;

    @Column(name = "currency", length = 3)
    private String currency = "USD";

    @Column(name = "employment_type", length = 50)
    private String employmentType;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_headcount")
    private Boolean isHeadcount = true;

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
