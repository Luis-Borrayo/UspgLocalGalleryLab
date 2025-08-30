package com.darwinruiz.uspglocalgallerylab.services;

import com.darwinruiz.uspglocalgallerylab.dto.UploadResult;
import com.darwinruiz.uspglocalgallerylab.repositories.IFileRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;

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
        int ok = 0, bad = 0;
        List<String> saved = new ArrayList<>();
        for (Part p : parts) {
            if (!"file".equals(p.getName()) || p.getSize() == 0) continue;
            String submitted = p.getSubmittedFileName();
            String fileName = Paths.get(submitted).getFileName().toString();
            // TODO: normalize + validate + save
            try (InputStream in = p.getInputStream()) {
                // ...
            }
        }
        return new UploadResult(ok, bad, saved);
    }
}
