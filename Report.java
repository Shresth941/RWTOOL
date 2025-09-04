package RwTool.rwtool.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Entity @Table(name = "reports")
public class Report {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;
    @Column(nullable = false) private String fileName;
    @Column(nullable = false) private String fileStoragePath;
    @Column(nullable = false) private LocalDateTime generatedDate = LocalDateTime.now();
    private String uploadedBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_type_id", nullable = false)
    private ReportType reportType;
}
