package RwTool.rwtool.controller;

import RwTool.rwtool.dto.ReportDto;
import RwTool.rwtool.entity.Report;
import RwTool.rwtool.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/reports/upload")
    public ResponseEntity<ReportDto> handleIntelligentUpload(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        
        UUID uploaderId = UUID.fromString(authentication.getName());
        
        Report savedReport = adminService.intelligentUpload(file, uploaderId);

        ReportDto responseDto = convertToDto(savedReport);
        
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    private ReportDto convertToDto(Report report) {
        // Create and return a DTO from the Report entity
        ReportDto dto = new ReportDto();
        // Set fields...
        return dto;
    }
}
