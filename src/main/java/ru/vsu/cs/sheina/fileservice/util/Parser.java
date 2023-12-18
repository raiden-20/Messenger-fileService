package ru.vsu.cs.sheina.fileservice.util;

public class Parser {

    public static String getFileName(String url) {
        String[] args = url.split("/");
        return args[3];
    }
}
