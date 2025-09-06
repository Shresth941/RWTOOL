package RwTool.rwtool.controller;

import RwTool.rwtool.entity.ReportType;
import RwTool.rwtool.service.ReportTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report-types")
@CrossOrigin(origins = "http://localhost:3000")
public class ReportTypeController {

    private final ReportTypeService service;

    public ReportTypeController(ReportTypeService service) {
        this.service = service;
    }

    // @PreAuthorize("hasRole('WEALTH_ADMIN')")
    @PostMapping
    public ResponseEntity<ReportType> create(@RequestParam String name,
                                             @RequestParam(required = false) String sourcePath,
                                             @RequestParam(required = false) String outputFolder) {
        return ResponseEntity.ok(service.create(name, sourcePath, outputFolder));
    }

    @GetMapping
    public ResponseEntity<List<ReportType>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportType> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
