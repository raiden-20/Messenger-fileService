package ru.vsu.cs.sheina.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.cs.sheina.fileservice.service.enums.FileSource;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UrlDTO {

    String sourseId;

    String url;

    FileSource source;
}
