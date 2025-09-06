package RwTool.rwtool.service;

import RwTool.rwtool.entity.Role;
import RwTool.rwtool.exceptions.NotFoundException;
import RwTool.rwtool.repo.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository repo;

    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }

    public Role createRole(String name, String permissions) {
        Role r = new Role();
        r.setName(name);
        r.setPermissions(permissions);
        return repo.save(r);
    }

    public Role getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Role not found with id: " + id));
    }

    public Role getByName(String name) {
        Role r = repo.findByName(name);
        if (r == null) throw new NotFoundException("Role not found: " + name);
        return r;
    }

    public List<Role> getAll() {
        return repo.findAll();
    }
}
