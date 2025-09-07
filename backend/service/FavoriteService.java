package RwTool.rwtool.service;

import RwTool.rwtool.dto.FavoriteDto;
import RwTool.rwtool.entity.Favorite;
import RwTool.rwtool.entity.Report;
import RwTool.rwtool.entity.User;
import RwTool.rwtool.repo.FavoriteRepository;
import RwTool.rwtool.repo.ReportRepository;
import RwTool.rwtool.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public FavoriteDto addFavorite(FavoriteDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Report report = reportRepository.findById(dto.getReportId()).orElseThrow(() -> new RuntimeException("Report not found"));

        Favorite fav = favoriteRepository.findByUser_UserIdAndReport_Id(user.getId(), report.getId()).orElse(null);
        if (fav == null) {
            fav = Favorite.builder().user(user).report(report).createdAt(LocalDateTime.now()).build();
            fav = favoriteRepository.save(fav);
        }

        return FavoriteDto.builder()
                .id(fav.getId())
                .userId(user.getId())
                .reportId(report.getId())
                .createdAt(fav.getCreatedAt())
                .build();
    }

    public List<FavoriteDto> getFavoritesForUser(Long userId) {
        List<Favorite> list = favoriteRepository.findByUser_UserIdOrderByBookmarkedAtDesc(userId);
        return list.stream().map(f -> FavoriteDto.builder()
                .id(f.getId())
                .userId(f.getUser().getId())
                .reportId(f.getReport().getId())
                .createdAt(f.getCreatedAt())
                .build()).collect(Collectors.toList());
    }

    @Transactional
    public void removeFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }
}
