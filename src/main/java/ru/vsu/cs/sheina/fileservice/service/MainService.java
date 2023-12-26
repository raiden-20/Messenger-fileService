package ru.vsu.cs.sheina.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.sheina.fileservice.configuration.minio.MinioBucket;
import ru.vsu.cs.sheina.fileservice.dto.BlogUrlDTO;
import ru.vsu.cs.sheina.fileservice.dto.FileSocialDTO;
import ru.vsu.cs.sheina.fileservice.dto.SocialUrlDTO;
import ru.vsu.cs.sheina.fileservice.exceptions.FileTooBigException;
import ru.vsu.cs.sheina.fileservice.dto.enums.FileSource;
import ru.vsu.cs.sheina.fileservice.util.JwtTokenUtil;
import ru.vsu.cs.sheina.fileservice.util.Parser;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MainService {

    private final MinioService minioService;
    private final SocialSender socialSender;
    private final BlogSender blogSender;
    private final JwtTokenUtil jwtTokenUtil;
    private final Integer FILE_MAX_SIZE = 2 * 1024 * 1024;

    @Value("${public.host}")
    private String storageHost;

    public void postSocialFile(MultipartFile file, String url, String source, String token) {
        UUID currentId = jwtTokenUtil.retrieveIdClaim(token);

        if (!file.isEmpty() && file.getSize() > FILE_MAX_SIZE) {
            throw new FileTooBigException();
        }

        if (!url.equals("undefined")) {
            String oldFileName = Parser.getFileName(url);

            minioService.deleteFile(oldFileName);
        }

        minioService.saveFile(file);
        String newUrl = storageHost + "/" + MinioBucket.PICTURE.toString() + "/" + file.getOriginalFilename();

        switch (FileSource.valueOf(source)) {
            case AVATAR -> socialSender.sendMessageToSocial(new SocialUrlDTO(currentId, newUrl, FileSource.AVATAR));
            case COVER -> socialSender.sendMessageToSocial(new SocialUrlDTO(currentId, newUrl, FileSource.COVER));
        }
    }

    public void postBlogFile(MultipartFile file, Integer postId) {
        if (!file.isEmpty() && file.getSize() > FILE_MAX_SIZE) {
            throw new FileTooBigException();
        }

        minioService.saveFile(file);

        String newUrl = storageHost + "/" + MinioBucket.PICTURE.toString() + "/" + file.getOriginalFilename();
        blogSender.sendMessageToPost(new BlogUrlDTO(newUrl, 0, postId));
    }

    public void deleteSocialFile(FileSocialDTO socialDTO, String token) {
        UUID currentId = jwtTokenUtil.retrieveIdClaim(token);

        if (!socialDTO.getUrl().isEmpty()) {
            String oldFileName = Parser.getFileName(socialDTO.getUrl());
            minioService.deleteFile(oldFileName);
        }
        switch (socialDTO.getSource()) {
            case AVATAR -> socialSender.sendMessageToSocial(new SocialUrlDTO(currentId, "", FileSource.AVATAR));
            case COVER -> socialSender.sendMessageToSocial(new SocialUrlDTO(currentId, "", FileSource.COVER));
        }
    }

    public void deleteBlogFile(BlogUrlDTO blogDTO, String token) {
        if (!blogDTO.getUrl().isEmpty()) {
            String oldFileName = Parser.getFileName(blogDTO.getUrl());
            minioService.deleteFile(oldFileName);
        }
        blogSender.sendMessageToPost(new BlogUrlDTO("", blogDTO.getPhotoId(), blogDTO.getPostId()));
    }
}
