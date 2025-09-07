package RwTool.rwtool.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileProcessorService {

    /**
     * Move a file from sourcePath to destinationPath (both absolute paths).
     * Creates destination parent directories if needed.
     */
    public void moveFile(String sourcePath, String destinationPath) throws IOException {
        Path src = Paths.get(sourcePath);
        Path dest = Paths.get(destinationPath);
        Files.createDirectories(dest.getParent());
        Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
    }
}
