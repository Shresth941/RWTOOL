package RwTool.rwtool.service;

import RwTool.rwtool.dto.ReportTypeDto;
import RwTool.rwtool.entity.ReportType;
import RwTool.rwtool.repo.ReportTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportTypeService {

    private final ReportTypeRepository reportTypeRepository;

    public ReportTypeDto createReportType(ReportTypeDto dto) {
        ReportType t = ReportType.builder().name(dto.getName()).description(dto.getDescription()).build();
        t = reportTypeRepository.save(t);
        return ReportTypeDto.builder().id(t.getId()).name(t.getName()).description(t.getDescription()).build();
    }

    public List<ReportTypeDto> getAll() {
        return reportTypeRepository.findAll().stream().map(t -> ReportTypeDto.builder()
                .id(t.getId()).name(t.getName()).description(t.getDescription()).build()).collect(Collectors.toList());
    }
}
