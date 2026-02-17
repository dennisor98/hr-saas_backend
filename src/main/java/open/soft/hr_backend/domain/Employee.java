package open.soft.hr_backend.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "employees", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tenant_id", "employee_number"})
})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, columnDefinition = "UUID")
    private Tenant tenant;

    @Column(name = "employee_number", length = 50)
    private String employeeNumber;

    @Column(name = "status", length = 50)
    private String status = "ACTIVE";

    // Personal details
    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "preferred_name", length = 100)
    private String preferredName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "marital_status", length = 50)
    private String maritalStatus;

    @Column(name = "nationality", length = 100)
    private String nationality;

    // Contact
    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "personal_email", length = 255)
    private String personalEmail;

    @Column(name = "work_phone", length = 50)
    private String workPhone;

    @Column(name = "mobile_phone", length = 50)
    private String mobilePhone;

    @Column(name = "home_phone", length = 50)
    private String homePhone;

    // Address
    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "postal_code", length = 50)
    private String postalCode;

    @Column(name = "country_code", length = 2)
    private String countryCode;

    // Employment
    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "original_hire_date")
    private LocalDate originalHireDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @Column(name = "probation_end_date")
    private LocalDate probationEndDate;

    @Column(name = "notice_date")
    private LocalDate noticeDate;

    @Column(name = "exit_date")
    private LocalDate exitDate;

    @Column(name = "rehire_date")
    private LocalDate rehireDate;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Column(name = "department_id", columnDefinition = "UUID")
    private UUID departmentId;

    @Column(name = "position_id", columnDefinition = "UUID")
    private UUID positionId;

    @Column(name = "location_id", columnDefinition = "UUID")
    private UUID locationId;

    @Column(name = "cost_center_id", columnDefinition = "UUID")
    private UUID costCenterId;

    // Compensation snapshot
    @Column(name = "pay_type", length = 50)
    private String payType;

    @Column(name = "pay_rate", precision = 15, scale = 2)
    private BigDecimal payRate;

    @Column(name = "pay_period", length = 50)
    private String payPeriod;

    @Column(name = "currency", length = 3)
    private String currency = "USD";

    // System fields
    @Column(name = "custom_fields", columnDefinition = "JSONB DEFAULT '{}'")
    private String customFields = "{}";

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

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
