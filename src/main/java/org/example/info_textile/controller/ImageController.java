package org.example.info_textile.controller;

import org.example.info_textile.model.Image;
import org.example.info_textile.repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/images")
public class ImageController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private ImageRepo repository;

    @PostMapping("/upload")
    public ResponseEntity<Image> upload(@RequestParam("file") MultipartFile file,
                                        @RequestParam("caption") String caption) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setUrl("/images/" + fileName);
        image.setCaption(caption);
        repository.save(image);

        return ResponseEntity.ok(image);
    }

    @GetMapping
    public List<Image> list() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws IOException {
        Image image = repository.findById(id).orElseThrow();
        Files.deleteIfExists(Paths.get("uploads/" + image.getUrl().replace("/images/", "")));
        repository.delete(image);
        return ResponseEntity.ok().build();
    }
}

