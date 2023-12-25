package ru.vsu.cs.sheina.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.vsu.cs.sheina.fileservice.service.enums.FileSource;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SocialUrlDTO {

    String sourceId;

    String url;

    FileSource source;
}
