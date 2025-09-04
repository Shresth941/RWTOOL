package RwTool.rwtool.services;

import RwTool.rwtool.entity.ReportType;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
public class FileStorageService {
    private final Path rootLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public String store(MultipartFile file, ReportType reportType) {
        if (file.isEmpty()) throw new IllegalArgumentException("Failed to store empty file.");
        if (reportType == null || reportType.getName() == null || reportType.getName().isBlank()) {
            throw new IllegalArgumentException("Cannot store file: ReportType is invalid.");
        }
        try {
            Path targetDirectory = this.rootLocation.resolve(reportType.getName());
            Files.createDirectories(targetDirectory);
            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path destinationFile = targetDirectory.resolve(uniqueFileName).normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return destinationFile.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
}
