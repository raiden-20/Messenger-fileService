package ru.vsu.cs.sheina.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.vsu.cs.sheina.fileservice.service.enums.FileSource;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UrlDTO {

    String sourceId;

    String url;

    FileSource source;
}
