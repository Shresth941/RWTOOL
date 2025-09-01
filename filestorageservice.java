import com.wealthcore.entity.ReportType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path rootLocation;

    // Inject the upload directory from application.properties
    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
    }

    // This method runs once after the service is created to ensure the root directory exists.
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    /**
     * Stores a file in a dynamic sub-directory based on the report type.
     *
     * @param file       The file to store.
     * @param reportType The type of report, used to determine the sub-directory.
     * @return The full path where the file was stored.
     */
    public String store(MultipartFile file, ReportType reportType) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Failed to store empty file.");
            }

            // Step I: Construct the dynamic storage path (e.g., ./local-file-storage/KYC Reports)
            Path targetDirectory = this.rootLocation.resolve(reportType.getName());

            // Step J: Create the directory if it doesn't exist
            Files.createDirectories(targetDirectory);

            // Step K: Generate a unique file name to prevent overwrites and for security
            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path destinationFile = targetDirectory.resolve(uniqueFileName).normalize().toAbsolutePath();

            // Step L: Save the file
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            return destinationFile.toString();

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
}
