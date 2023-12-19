package ru.vsu.cs.sheina.fileservice.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.sheina.fileservice.configuration.minio.MinioBucket;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(MinioBucket.PICTURE.toString()).object(fileName).build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveFile(MultipartFile file) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(MinioBucket.PICTURE.toString())
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), 5 * 1024 * 1024)
                    .build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
