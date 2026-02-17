package open.soft.hr_backend.domain;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private String id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "subdomain", unique = true, length = 100)
    private String subdomain;

    @Column(name = "status", length = 50)
    private String status = "ACTIVE";

    @Column(name = "settings", columnDefinition = "JSONB DEFAULT '{}'")
    private String settings = "{}";

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