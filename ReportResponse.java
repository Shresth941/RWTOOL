package RwTool.rwtool.dto;

import java.time.LocalDateTime;

public class ReportResponse {
    private Long reportId;
    private String fileName;
    private String fileStoragePath;
    private LocalDateTime generatedDate;
    private String uploadedBy;

    public ReportResponse(Long reportId, String fileName, String fileStoragePath, LocalDateTime generatedDate, String uploadedBy) {
        this.reportId = reportId;
        this.fileName = fileName;
        this.fileStoragePath = fileStoragePath;
        this.generatedDate = generatedDate;
        this.uploadedBy = uploadedBy;
    }

  
}
