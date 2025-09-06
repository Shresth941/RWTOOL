package RwTool.rwtool.service;

import RwTool.rwtool.dto.ReportRequest;
import RwTool.rwtool.dto.ReportResponse;
import RwTool.rwtool.entity.Report;
import RwTool.rwtool.entity.ReportType;
import RwTool.rwtool.exceptions.NotFoundException;
import RwTool.rwtool.repo.ReportRepository;
import RwTool.rwtool.repo.ReportTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ReportRepository repo;
    private final ReportTypeRepository reportTypeRepo;

    public ReportService(ReportRepository repo, ReportTypeRepository reportTypeRepo) {
        this.repo = repo;
        this.reportTypeRepo = reportTypeRepo;
    }

    public List<ReportResponse> getAllReports() {
        return repo.findAllByOrderByGeneratedDateDesc().stream().map(this::map).collect(Collectors.toList());
    }

    public Page<Report> getAllReportsPaged(Pageable pageable) {
        // useful for frontend pagination
        return repo.findAll(pageable);
    }

    public ReportResponse addReport(ReportRequest req) {
        Report r = new Report();
        r.setName(req.getName());
        r.setPath(req.getPath());
        r.setGeneratedDate(req.getGeneratedDate() != null ? req.getGeneratedDate() : LocalDateTime.now());
        r.setUploadedBy(req.getUploadedBy());

        if (req.getReportTypeId() != null) {
            ReportType rt = reportTypeRepo.findById(req.getReportTypeId())
                    .orElseThrow(() -> new NotFoundException("ReportType not found with id: " + req.getReportTypeId()));
            r.setReportType(rt);
        }
        Report saved = repo.save(r);
        return map(saved);
    }

    public ReportResponse updateReport(Long id, ReportRequest req) {
        Report r = repo.findById(id).orElseThrow(() -> new NotFoundException("Report not found with id: " + id));
        r.setName(req.getName());
        r.setPath(req.getPath());
        r.setGeneratedDate(req.getGeneratedDate() != null ? req.getGeneratedDate() : r.getGeneratedDate());
        r.setUploadedBy(req.getUploadedBy());
        if (req.getReportTypeId() != null) {
            ReportType rt = reportTypeRepo.findById(req.getReportTypeId())
                    .orElseThrow(() -> new NotFoundException("ReportType not found with id: " + req.getReportTypeId()));
            r.setReportType(rt);
        }
        return map(repo.save(r));
    }

    public void deleteReport(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Report not found with id: " + id);
        repo.deleteById(id);
    }

    public List<ReportResponse> searchByName(String name) {
        return repo.findByNameContainingIgnoreCase(name).stream().map(this::map).collect(Collectors.toList());
    }

    public List<ReportResponse> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return repo.findByGeneratedDateBetween(start, end).stream().map(this::map).collect(Collectors.toList());
    }

    public Report getEntityById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Report not found with id: " + id));
    }

    private ReportResponse map(Report r) {
        return new ReportResponse(r.getId(), r.getName(), r.getPath(), r.getGeneratedDate(), r.getUploadedBy(),
                r.getReportType() != null ? r.getReportType().getReportTypeId() : null);
    }
}
