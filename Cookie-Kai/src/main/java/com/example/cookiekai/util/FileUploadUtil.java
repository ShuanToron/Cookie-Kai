package com.example.cookiekai.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String uploadDirectory, String fileName, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream stream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(stream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Couldn't save the file " + fileName, e);
        }
    }

    public static void cleanDirectory(String directory) throws IOException {
        Path dirPath = Paths.get(directory);
        try {
            Files.list(dirPath).forEach(file -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        System.out.println("Couldn't delete file " + file);
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Couldn't list directory");
        }
    }

}
