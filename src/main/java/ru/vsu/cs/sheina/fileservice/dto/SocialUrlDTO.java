package ru.vsu.cs.sheina.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.vsu.cs.sheina.fileservice.dto.enums.FileSource;

import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SocialUrlDTO {

    UUID sourceId;

    String url;

    FileSource source;
}
