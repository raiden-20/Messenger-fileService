package ru.vsu.cs.sheina.fileservice.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestPart;
import ru.vsu.cs.sheina.fileservice.service.enums.FileSource;

@Data
public class FileDTO {

    String url;

    FileSource source;

    Integer postId;

    Integer photoId;
}
