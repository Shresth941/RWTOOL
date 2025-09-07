package RwTool.rwtool.service;

import RwTool.rwtool.dto.UserReportRequest;
import RwTool.rwtool.dto.UserReportResponse;
import RwTool.rwtool.entity.UserReport;
import RwTool.rwtool.repo.UserReportRepository;
import RwTool.rwtool.repo.UserRepository;
import RwTool.rwtool.repo.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserReportService {

    private final UserReportRepository userReportRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public UserReportResponse assignReport(UserReportRequest req) {
        var user = userRepository.findById(req.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        var report = reportRepository.findById(req.getReportId()).orElseThrow(() -> new RuntimeException("Report not found"));

        UserReport ur = userReportRepository.findByUserId(user.getId()).stream()
                .filter(x -> x.getReport().getId().equals(report.getId()))
                .findFirst().orElse(null);

        if (ur == null) {
            ur = UserReport.builder()
                    .user(user)
                    .report(report)
                    .status(req.getStatus())
                    .comments(req.getComments())
                    .assignedAt(LocalDateTime.now())
                    .build();
        } else {
            ur.setStatus(req.getStatus());
            ur.setComments(req.getComments());
        }
        ur = userReportRepository.save(ur);
        return toDto(ur);
    }

    public List<UserReportResponse> getRecentReportsForUser(Long userId) {
        List<UserReport> list = userReportRepository.findTop5ByUserIdOrderByLastAccessedAtDesc(userId);
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<UserReportResponse> getReportsForUser(Long userId) {
        List<UserReport> list = userReportRepository.findByUserId(userId);
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    private UserReportResponse toDto(UserReport ur) {
        return UserReportResponse.builder()
                .id(ur.getId())
                .status(ur.getStatus())
                .comments(ur.getComments())
                .assignedAt(ur.getAssignedAt())
                .lastAccessedAt(ur.getLastAccessedAt())
                .user(null) // fill if needed (UserResponse)
                .report(null) // fill if needed (ReportResponse)
                .build();
    }
}
