package RwTool.rwtool.controller;

import RwTool.rwtool.dto.ApiResponse;
import RwTool.rwtool.dto.ReportTypeDto;
import RwTool.rwtool.service.ReportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/report-types")
@RequiredArgsConstructor
public class ReportTypeController {

    private final ReportTypeService reportTypeService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReportTypeDto>> create(@Valid @RequestBody ReportTypeDto dto) {
        ReportTypeDto created = reportTypeService.createReportType(dto);
        return ResponseEntity.status(201).body(ApiResponse.success("ReportType created", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReportTypeDto>>> all() {
        List<ReportTypeDto> list = reportTypeService.getAll();
        return ResponseEntity.ok(ApiResponse.success("Report types", list));
    }
}
