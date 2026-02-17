package open.soft.hr_backend.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "work_schedules")
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, columnDefinition = "UUID")
    private Tenant tenant;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "monday_hours", precision = 4, scale = 2)
    private BigDecimal mondayHours;

    @Column(name = "tuesday_hours", precision = 4, scale = 2)
    private BigDecimal tuesdayHours;

    @Column(name = "wednesday_hours", precision = 4, scale = 2)
    private BigDecimal wednesdayHours;

    @Column(name = "thursday_hours", precision = 4, scale = 2)
    private BigDecimal thursdayHours;

    @Column(name = "friday_hours", precision = 4, scale = 2)
    private BigDecimal fridayHours;

    @Column(name = "saturday_hours", precision = 4, scale = 2)
    private BigDecimal saturdayHours;

    @Column(name = "sunday_hours", precision = 4, scale = 2)
    private BigDecimal sundayHours;

    @Column(name = "is_flexible")
    private Boolean isFlexible = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;

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