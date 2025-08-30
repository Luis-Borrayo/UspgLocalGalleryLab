package com.darwinruiz.uspglocalgallerylab.services;

import com.darwinruiz.uspglocalgallerylab.dto.UploadResult;
import com.darwinruiz.uspglocalgallerylab.repositories.IFileRepository;
import com.darwinruiz.uspglocalgallerylab.util.ImageValidator;
import com.darwinruiz.uspglocalgallerylab.util.NamePolicy;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import java.time.LocalDate;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImageService {
    private final IFileRepository repo;
    public ImageService(IFileRepository repo) { this.repo = repo; }

    /** TODO-3:
     *  - iterar partes "file"
     *  - normalizar nombre (NamePolicy.normalize)
     *  - validar (ImageValidator.isValid)
     *  - subcarpeta = NamePolicy.datedSubdir(LocalDate.now())
     *  - repo.save(subdir, fileName, data)
     *  - contar subidos/rechazados
     */
    public UploadResult uploadLocalImages(Collection<Part> parts) throws IOException, ServletException {
        if (parts == null || parts.isEmpty()) {
            return new UploadResult(0, 0, List.of());
        }

        int ok = 0, bad = 0;
        List<String> saved = new ArrayList<>();
        
        for (Part part : parts) {
            try {
                // Skip non-file parts or empty files
                if (part == null || !"file".equals(part.getName()) || part.getSize() == 0) {
                    continue;
                }
                
                String submittedFileName = part.getSubmittedFileName();
                if (submittedFileName == null || submittedFileName.isBlank()) {
                    bad++;
                    continue;
                }
                
                // Normalize the filename
                String fileName = com.darwinruiz.uspglocalgallerylab.util.NamePolicy.normalize(submittedFileName);
                
                // Validate the file
                if (!com.darwinruiz.uspglocalgallerylab.util.ImageValidator.isValid(part, fileName)) {
                    bad++;
                    continue;
                }
                
                // Create dated subdirectory
                String subdir = com.darwinruiz.uspglocalgallerylab.util.NamePolicy.datedSubdir(LocalDate.now());
                
                // Save the file
                try (InputStream in = part.getInputStream()) {
                    String savedPath = repo.save(subdir, fileName, in);
                    if (savedPath != null && !savedPath.isBlank()) {
                        saved.add(savedPath);
                        ok++;
                    } else {
                        bad++;
                    }
                }
            } catch (Exception e) {
                bad++;
                // Log the error if needed
                e.printStackTrace();
            }
        }
        
        return new UploadResult(ok, bad, saved);
    }
}
