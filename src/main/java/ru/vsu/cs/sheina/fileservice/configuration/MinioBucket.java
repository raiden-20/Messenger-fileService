package ru.vsu.cs.sheina.fileservice.configuration;

public enum MinioBucket {
    PICTURE("picture");

    private String name;

    MinioBucket(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
