package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action; // e.g., CREATE_USER, UPLOAD_REPORT

    private String entityName; // User, Report, etc.

    private Long entityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by_user_id")
    private User performedBy;

    private LocalDateTime performedAt;

    @Column(length = 4000)
    private String details;

    @PrePersist
    protected void onCreate() {
        performedAt = LocalDateTime.now();
    }
}
