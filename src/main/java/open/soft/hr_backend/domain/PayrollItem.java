package open.soft.hr_backend.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "payroll_items")
public class PayrollItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_run_id", nullable = false, columnDefinition = "UUID")
    private PayrollRun payrollRun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, columnDefinition = "UUID")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false, columnDefinition = "UUID")
    private Tenant tenant;

    @Column(name = "regular_hours", precision = 7, scale = 2)
    private BigDecimal regularHours;

    @Column(name = "overtime_hours", precision = 7, scale = 2)
    private BigDecimal overtimeHours;

    @Column(name = "regular_pay", precision = 15, scale = 2)
    private BigDecimal regularPay;

    @Column(name = "overtime_pay", precision = 15, scale = 2)
    private BigDecimal overtimePay;

    @Column(name = "gross_pay", precision = 15, scale = 2)
    private BigDecimal grossPay;

    @Column(name = "net_pay", precision = 15, scale = 2)
    private BigDecimal netPay;

    @Column(name = "deductions", columnDefinition = "JSONB")
    private String deductions;

    @Column(name = "taxes", columnDefinition = "JSONB")
    private String taxes;

    @Column(name = "reimbursements", columnDefinition = "JSONB")
    private String reimbursements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankDetail bankAccount;

    @Column(name = "status", length = 50)
    private String status;

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
