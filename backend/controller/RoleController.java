package RwTool.rwtool.controller;

import RwTool.rwtool.dto.ApiResponse;
import RwTool.rwtool.dto.RoleDto;
import RwTool.rwtool.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleDto>> createRole(@Valid @RequestBody RoleDto dto) {
        RoleDto r = roleService.createRole(dto);
        return ResponseEntity.status(201).body(ApiResponse.success("Role created", r));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleDto>>> getAll() {
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(ApiResponse.success("Roles fetched", roles));
    }
}
