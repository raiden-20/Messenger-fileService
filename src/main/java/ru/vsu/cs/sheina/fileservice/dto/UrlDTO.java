package ru.vsu.cs.sheina.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.cs.sheina.fileservice.service.enums.FileSource;

@Data
@AllArgsConstructor
public class UrlDTO {

    String sourceId;

    String url;

    FileSource source;
}
