package RwTool.rwtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportTypeDto {
    private Long reportTypeId;
    private String name;
    private String sourcePath;
    private String outputFolder;
}
