package RwTool.rwtool.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import RwTool.rwtool.entity.Report;
import RwTool.rwtool.repo.ReportRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZipService {

    private final ReportRepository reportRepository;
    private final FileStorageService fileStorageService;

    /**
     * Create a temporary ZIP file containing reports with generatedDate between from and to (inclusive).
     * The controller will stream this file and delete it later if desired.
     */
    public File createZipForDateRange(String from, String to) throws IOException {
        // parse dates (assume format yyyy-MM-dd)
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        // convert to LocalDateTime range (start/end of day)
        var start = fromDate.atStartOfDay();
        var end = toDate.atTime(23, 59, 59);

        List<Report> reports = reportRepository.findByGeneratedDateBetween(start, end);

        if (reports.isEmpty()) {
            // create an empty zip with a README
            File empty = File.createTempFile("reports_empty_", ".zip");
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(empty))) {
                zos.putNextEntry(new ZipEntry("README.txt"));
                zos.write("No reports found in the specified range.".getBytes());
                zos.closeEntry();
            }
            return empty;
        }

        File zipFile = File.createTempFile("reports_", ".zip");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (Report r : reports) {
                if (r.getFilePath() == null) continue;
                Path p = fileStorageService.toPath(r.getFilePath());
                if (!Files.exists(p)) continue;
                ZipEntry entry = new ZipEntry(p.getFileName().toString());
                zos.putNextEntry(entry);
                try (InputStream in = Files.newInputStream(p)) {
                    byte[] buffer = new byte[8192];
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
                zos.closeEntry();
            }
        }
        return zipFile;
    }
}
