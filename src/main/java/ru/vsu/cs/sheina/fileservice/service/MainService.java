package ru.vsu.cs.sheina.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.sheina.fileservice.dto.FileDTO;
import ru.vsu.cs.sheina.fileservice.dto.UrlDTO;
import ru.vsu.cs.sheina.fileservice.exceptions.FileTooBigException;
import ru.vsu.cs.sheina.fileservice.service.enums.FileSource;
import ru.vsu.cs.sheina.fileservice.util.JwtTokenUtil;
import ru.vsu.cs.sheina.fileservice.util.Parser;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MainService {

    private final MinioService minioService;
    private final RabbitService rabbitService;
    private final JwtTokenUtil jwtTokenUtil;
    private final Integer FILE_MAX_SIZE = 2 * 1024 * 1024;

    @Value("${minio.host}")
    private String storageHost;
    @Value("${minio.bucket}")
    private String bucket;

    public void deleteFile(MultipartFile file, FileDTO fileDTO, String token) {
        UUID currentId = jwtTokenUtil.retrieveIdClaim(token);
        String fileName = Parser.getFileName(fileDTO.getUrl());
        minioService.deleteFile(fileName);

        switch (fileDTO.getSource()) {
            case AVATAR-> rabbitService.sendMessageToSocial(new UrlDTO(currentId.toString(), "", FileSource.AVATAR));
            case COVER -> rabbitService.sendMessageToSocial(new UrlDTO(currentId.toString(), "", FileSource.COVER));
            //TODO case POST -> rabbitService.sendMessageToPost(new UrlDTO(fileDTO.getPostId().toString(), "", FileSource.POST));
        }
    }

    public void saveFile(MultipartFile file, FileDTO fileDTO, String token) {
        UUID currentId = jwtTokenUtil.retrieveIdClaim(token);
        if (file.isEmpty() && file.getSize() > FILE_MAX_SIZE) {
            throw new FileTooBigException();
        }

        String oldFileName = Parser.getFileName(fileDTO.getUrl());
        minioService.deleteFile(oldFileName);

        minioService.saveFile(file);
        String newUrl = storageHost + "/" + bucket + "/" + file.getOriginalFilename();

        switch (fileDTO.getSource()) {
            case AVATAR -> rabbitService.sendMessageToSocial(new UrlDTO(currentId.toString(), newUrl, FileSource.AVATAR));
            case COVER -> rabbitService.sendMessageToSocial(new UrlDTO(currentId.toString(), newUrl, FileSource.COVER));
            //TODO case POST -> rabbitService.sendMessageToPost(new UrlDTO(fileDTO.getPostId().toString(), newUrl, FileSource.POST));
        }
    }
}
