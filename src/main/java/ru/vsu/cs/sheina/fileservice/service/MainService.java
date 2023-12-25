package ru.vsu.cs.sheina.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.sheina.fileservice.configuration.minio.MinioBucket;
import ru.vsu.cs.sheina.fileservice.dto.BlogUrlDTO;
import ru.vsu.cs.sheina.fileservice.dto.FileDTO;
import ru.vsu.cs.sheina.fileservice.dto.SocialUrlDTO;
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

    @Value("${public.host}")
    private String storageHost;

    public void deleteFile(MultipartFile file, FileDTO fileDTO, String token) {
        UUID currentId = jwtTokenUtil.retrieveIdClaim(token);
        String fileName = Parser.getFileName(fileDTO.getUrl());
        minioService.deleteFile(fileName);

        switch (fileDTO.getSource()) {
            case AVATAR-> rabbitService.sendMessageToSocial(new SocialUrlDTO(currentId.toString(), "",  FileSource.AVATAR ));
            case COVER -> rabbitService.sendMessageToSocial(new SocialUrlDTO(currentId.toString(), "",  FileSource.COVER));
            case POST -> rabbitService.sendMessageToPost(new BlogUrlDTO(fileDTO.getPostId().toString(), fileDTO.getUrl(), fileDTO.getPhotoId()));
        }
    }

    public void saveFile(MultipartFile file, FileDTO fileDTO,String token) {
        UUID currentId = jwtTokenUtil.retrieveIdClaim(token);
        if (file.isEmpty() && file.getSize() > FILE_MAX_SIZE) {
            throw new FileTooBigException();
        }

        if (!fileDTO.getUrl().isEmpty()) {
            String oldFileName = Parser.getFileName(fileDTO.getUrl());
            minioService.deleteFile(oldFileName);
        }

        minioService.saveFile(file);
        String newUrl = storageHost + "/" + MinioBucket.PICTURE.toString() + "/" + file.getOriginalFilename();
        switch (fileDTO.getSource()) {
            case AVATAR -> rabbitService.sendMessageToSocial(new SocialUrlDTO(currentId.toString(), newUrl, FileSource.AVATAR));
            case COVER -> rabbitService.sendMessageToSocial(new SocialUrlDTO(currentId.toString(), newUrl, FileSource.COVER));
            case POST -> rabbitService.sendMessageToPost(new BlogUrlDTO(fileDTO.getPostId().toString(), newUrl, 0));
        }
    }
}
