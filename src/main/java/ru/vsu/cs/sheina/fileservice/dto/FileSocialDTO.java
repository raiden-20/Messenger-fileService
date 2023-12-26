package ru.vsu.cs.sheina.fileservice.dto;

import lombok.Data;
import ru.vsu.cs.sheina.fileservice.dto.enums.FileSource;

@Data
public class FileSocialDTO {

    String url;

    FileSource source;
}
