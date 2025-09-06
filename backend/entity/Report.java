package RwTool.rwtool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports",
       indexes = {
           @Index(name = "idx_report_generated_date", columnList = "generated_date"),
           @Index(name = "idx_report_name", columnList = "file_name")
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String name;

    // absolute filesystem path where the file is stored
    @Column(name = "file_storage_path", nullable = false, length = 2000, unique = true)
    private String path;

    @Column(name = "generated_date", nullable = false)
    private LocalDateTime generatedDate;

    // optional relation to the uploader user (null for automated ingests)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by_user")
    @JsonIgnore
    private User uploadedByUser;

    // human readable uploader (convenience; not authoritative)
    @Column(name = "uploaded_by", length = 255)
    private String uploadedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;
}
