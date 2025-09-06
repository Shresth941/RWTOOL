package RwTool.rwtool.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "report_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_type_id")
    private Long reportTypeId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // optional source folder / ingestion path (e.g., SG/Retail/Customer)
    @Column(name = "source_path", length = 1000)
    private String sourcePath;

    // optional default output folder mapping
    @Column(name = "output_folder", length = 1000)
    private String outputFolder;
}
