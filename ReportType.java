package RwTool.rwtool.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "report_types")
public class ReportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_type_id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "source_path")
    private String sourcePath;
}
