package RwTool.rwtool.service;

import RwTool.rwtool.entity.ReportType;
import RwTool.rwtool.exceptions.NotFoundException;
import RwTool.rwtool.repo.ReportTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportTypeService {
    private final ReportTypeRepository repo;

    public ReportTypeService(ReportTypeRepository repo) { this.repo = repo; }

    public ReportType create(String name, String sourcePath, String outputFolder) {
        ReportType rt = new ReportType();
        rt.setName(name);
        rt.setSourcePath(sourcePath);
        rt.setOutputFolder(outputFolder);
        return repo.save(rt);
    }

    public ReportType getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("ReportType not found with id: " + id));
    }

    public ReportType getByName(String name) {
        ReportType rt = repo.findByName(name);
        if (rt == null) throw new NotFoundException("ReportType not found: " + name);
        return rt;
    }

    public List<ReportType> getAll() { return repo.findAll(); }
}
