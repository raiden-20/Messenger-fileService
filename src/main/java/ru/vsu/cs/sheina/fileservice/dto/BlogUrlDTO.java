package ru.vsu.cs.sheina.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.vsu.cs.sheina.fileservice.service.enums.FileSource;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BlogUrlDTO {

    String sourceId;

    String url;

    Integer photoId;
}
