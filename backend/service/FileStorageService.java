package RwTool.rwtool.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String store(MultipartFile file) throws IOException {
        Path storagePath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(storagePath);

        String safeFilename = UUID.randomUUID().toString() + "_" + Paths.get(file.getOriginalFilename()).getFileName().toString();
        Path target = storagePath.resolve(safeFilename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toString();
    }

    public Path toPath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }
}
