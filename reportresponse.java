package RwTool.rwtool.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReportResponse {
    private final Long reportId;
    private final String fileName;
    private final String fileStoragePath;
    private final LocalDateTime generatedDate;
    private final String uploadedBy;

    public ReportResponse(Long reportId, String fileName, String fileStoragePath, LocalDateTime generatedDate, String uploadedBy) {
        this.reportId = reportId;
        this.fileName = fileName;
        this.fileStoragePath = fileStoragePath;
        this.generatedDate = generatedDate;
        this.uploadedBy = uploadedBy;
    }
}
