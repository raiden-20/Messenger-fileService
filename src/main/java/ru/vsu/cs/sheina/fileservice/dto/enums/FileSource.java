package ru.vsu.cs.sheina.fileservice.dto.enums;

public enum FileSource {
    AVATAR("AVATAR"),
    COVER("COVER");

    private String name;

    FileSource(String name) {
        this.name = name;
    }
}
