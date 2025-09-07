package RwTool.rwtool.controller;

import RwTool.rwtool.dto.ApiResponse;
import RwTool.rwtool.dto.FavoriteDto;
import RwTool.rwtool.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<ApiResponse<FavoriteDto>> addFavorite(@Valid @RequestBody FavoriteDto dto) {
        FavoriteDto created = favoriteService.addFavorite(dto);
        return ResponseEntity.ok(ApiResponse.success("Favorite added", created));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<FavoriteDto>>> getFavorites(@PathVariable Long userId) {
        List<FavoriteDto> list = favoriteService.getFavoritesForUser(userId);
        return ResponseEntity.ok(ApiResponse.success("Favorites fetched", list));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> remove(@PathVariable Long id) {
        favoriteService.removeFavorite(id);
        return ResponseEntity.ok(ApiResponse.success("Favorite removed", null));
    }
}
