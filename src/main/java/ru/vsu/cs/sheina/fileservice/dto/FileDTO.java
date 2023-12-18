package ru.vsu.cs.sheina.fileservice.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDTO {

    String url;

    MultipartFile file;
}
