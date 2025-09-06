package RwTool.rwtool.service;

import RwTool.rwtool.entity.Favorite;
import RwTool.rwtool.exceptions.NotFoundException;
import RwTool.rwtool.repo.FavoriteRepository;
import RwTool.rwtool.repo.ReportRepository;
import RwTool.rwtool.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class FavoriteService {

    private final FavoriteRepository favRepo;
    private final UserRepository userRepo;
    private final ReportRepository reportRepo;

    public FavoriteService(FavoriteRepository favRepo, UserRepository userRepo, ReportRepository reportRepo) {
        this.favRepo = favRepo;
        this.userRepo = userRepo;
        this.reportRepo = reportRepo;
    }

    @Transactional
    public void toggleFavorite(Long userId, Long reportId) {
        var maybe = favRepo.findByUser_UserIdAndReport_Id(userId, reportId);
        if (maybe.isPresent()) {
            favRepo.deleteByUser_UserIdAndReport_Id(userId, reportId);
            return;
        }
        var user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        var report = reportRepo.findById(reportId).orElseThrow(() -> new NotFoundException("Report not found"));
        Favorite f = new Favorite();
        f.setUser(user);
        f.setReport(report);
        f.setBookmarkedAt(LocalDateTime.now());
        favRepo.save(f);
    }
}
