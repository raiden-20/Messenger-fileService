package ru.vsu.cs.sheina.fileservice.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.sheina.fileservice.configuration.minio.MinioBucket;
import ru.vsu.cs.sheina.fileservice.dto.FileDTO;
import ru.vsu.cs.sheina.fileservice.util.Parser;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    public void deleteFile(FileDTO fileDTO) {
        try {
            String fileName = Parser.getFileName(fileDTO.getUrl());
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(MinioBucket.PICTURE.toString()).object(fileName).build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveFile(FileDTO fileDTO) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(MinioBucket.PICTURE.toString())
                    .object(fileDTO.getFile().getOriginalFilename())
                    .stream(fileDTO.getFile().getInputStream(), fileDTO.getFile().getSize(), 5 * 1024 * 1024)
                    .build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
