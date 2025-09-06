package RwTool.rwtool.controller;

import RwTool.rwtool.entity.Role;
import RwTool.rwtool.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    // Create role (admin)
    // @PreAuthorize("hasRole('WEALTH_ADMIN')")
    @PostMapping
    public ResponseEntity<Role> create(@RequestParam String name, @RequestParam(required = false) String permissions) {
        Role r = service.createRole(name, permissions);
        return ResponseEntity.ok(r);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<Role> getByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getByName(name));
    }

    @GetMapping
    public ResponseEntity<List<Role>> all() {
        return ResponseEntity.ok(service.getAll());
    }
}
