package RwTool.rwtool.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_storage_path", nullable = false)
    private String fileStoragePath;

    @Column(name = "generated_date", nullable = false)
    private LocalDateTime generatedDate = LocalDateTime.now();

    @Column(name = "uploaded_by")
    private String uploadedBy;

    // --- THIS IS THE CRITICAL NEW RELATIONSHIP ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_type_id", nullable = false)
    private ReportType reportType;

    // --- Getters & Setters ---
    // (Lombok @Data would be cleaner, but this works perfectly)
    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileStoragePath() { return fileStoragePath; }
    public void setFileStoragePath(String fileStoragePath) { this.fileStoragePath = fileStoragePath; }
    public LocalDateTime getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(LocalDateTime generatedDate) { this.generatedDate = generatedDate; }
    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }
    public ReportType getReportType() { return reportType; }
    public void setReportType(ReportType reportType) { this.reportType = reportType; }
}

