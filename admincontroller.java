package com.wealthcore.controller;

import com.wealthcore.dto.ReportDto;
import com.wealthcore.entity.Report;
import com.wealthcore.service.AdminService;
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

    // ... your other methods (user management, report type creation) ...

    /**
     * Endpoint for an admin to manually upload a report.
     */
    @PostMapping("/reports/upload")
    public ResponseEntity<ReportDto> handleManualUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("reportTypeId") UUID reportTypeId,
            Authentication authentication) { // Spring Security provides the logged-in user

        // Assuming the user's ID (UUID) is stored as the principal's name in the JWT
        UUID uploaderId = UUID.fromString(authentication.getName());

        // Step B: Call the service layer to handle the logic
        Report savedReport = adminService.manualUpload(file, reportTypeId, uploaderId);

        // Convert the saved Report entity to a DTO for the response
        ReportDto responseDto = convertToDto(savedReport); // You need to implement this conversion method

        // Step S: Return 201 Created with the details of the new report
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Helper method to convert Entity to DTO
    private ReportDto convertToDto(Report report) {
        ReportDto dto = new ReportDto();
        dto.setReportId(report.getReportId());
        dto.setFileName(report.getFileName());
        dto.setGeneratedDate(report.getGeneratedDate());
        dto.setReportTypeName(report.getReportType().getName());
        return dto;
    }
}
