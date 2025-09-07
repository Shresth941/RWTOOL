package RwTool.rwtool.service;

import RwTool.rwtool.dto.RoleDto;
import RwTool.rwtool.entity.Role;
import RwTool.rwtool.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleDto createRole(RoleDto dto) {
        Role r = Role.builder().name(dto.getName()).description(dto.getDescription()).build();
        r = roleRepository.save(r);
        return RoleDto.builder().id(r.getId()).name(r.getName()).description(r.getDescription()).build();
    }

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream().map(r -> RoleDto.builder()
                .id(r.getId())
                .name(r.getName())
                .description(r.getDescription())
                .build()).collect(Collectors.toList());
    }
}
