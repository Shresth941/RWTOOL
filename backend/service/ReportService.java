package RwTool.rwtool.service;

import RwTool.rwtool.dto.*;
import RwTool.rwtool.entity.Report;
import RwTool.rwtool.entity.ReportType;
import RwTool.rwtool.entity.User;
import RwTool.rwtool.repo.ReportRepository;
import RwTool.rwtool.repo.ReportTypeRepository;
import RwTool.rwtool.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportTypeRepository reportTypeRepository;
    private final UserRepository userRepository;

    // Pagination listing
    public Page<ReportResponse> getReports(Pageable pageable) {
        Page<Report> p = reportRepository.findAll(pageable);
        return p.map(this::toDto);
    }

    // Free-text search
    public List<ReportResponse> searchReports(String q) {
        List<Report> list = reportRepository.findByNameContainingIgnoreCase(q);
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    // Date range filter with pageable
    public Page<ReportResponse> filterReportsByDate(LocalDate from, LocalDate to, Pageable pageable) {
        var start = from.atStartOfDay();
        var end = to.atTime(23, 59, 59);
        Page<Report> p = reportRepository.findByGeneratedDateBetween(start, end, pageable);
        return p.map(this::toDto);
    }

    @Transactional
    public ReportResponse createReport(ReportRequest req) {
        Report r = new Report();
        r.setTitle(req.getTitle());
        r.setDescription(req.getDescription());
        r.setFileName(req.getFileName());
        r.setFileType(req.getFileType());

        if (req.getReportTypeId() != null) {
            reportTypeRepository.findById(req.getReportTypeId()).ifPresent(r::setReportType);
        }
        if (req.getCreatedByUserId() != null) {
            userRepository.findById(req.getCreatedByUserId()).ifPresent(r::setCreatedBy);
        }
        r.setCreatedAt(LocalDateTime.now());
        r = reportRepository.save(r);
        return toDto(r);
    }

    public ReportResponse getReportById(Long id) {
        Report r = reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
        return toDto(r);
    }

    // mapping
    private ReportResponse toDto(Report r) {
        ReportResponse dto = ReportResponse.builder()
                .id(r.getId())
                .title(r.getTitle())
                .description(r.getDescription())
                .fileName(r.getFileName())
                .filePath(r.getFilePath())
                .fileType(r.getFileType())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
        if (r.getReportType() != null) {
            dto.setReportType(ReportTypeDto.builder()
                    .id(r.getReportType().getId())
                    .name(r.getReportType().getName())
                    .description(r.getReportType().getDescription())
                    .build());
        }
        if (r.getCreatedBy() != null) {
            dto.setCreatedBy(UserResponse.builder()
                    .id(r.getCreatedBy().getId())
                    .username(r.getCreatedBy().getUsername())
                    .email(r.getCreatedBy().getEmail())
                    .fullName(r.getCreatedBy().getFullName())
                    .build());
        }
        return dto;
    }
}
