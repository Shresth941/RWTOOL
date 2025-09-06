package RwTool.rwtool.controller;

import RwTool.rwtool.dto.IngestRequest;
import RwTool.rwtool.service.IngestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingest")
@CrossOrigin(origins = "http://localhost:3000")
public class IngestController {

    private final IngestService ingestService;

    public IngestController(IngestService ingestService) {
        this.ingestService = ingestService;
    }

    /**
     * Source system calls this endpoint to notify RW Tool of a file event.
     * Supports actions:
     *  - "New"    → move file from incomingDir to uploadDir and create DB record.
     *  - "Delete" → delete file and remove DB record.
     *
     * @param req JSON body with ingest details
     * @return ResponseEntity with status message
     */
    @PostMapping
    public ResponseEntity<String> ingest(@RequestBody IngestRequest req) {
        if (req.getAction() == null || req.getInputFileName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid request: 'action' and 'inputFileName' are required");
        }

        try {
            ingestService.process(req);
            return ResponseEntity.ok("Ingest action processed successfully: " + req.getAction());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while processing ingest: " + ex.getMessage());
        }
    }
}
