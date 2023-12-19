package ru.vsu.cs.sheina.fileservice.exceptions;

import org.springframework.http.HttpStatus;

public class FileTooBigException extends AppException{
    public FileTooBigException() {
        super("File too big", HttpStatus.BAD_REQUEST);
    }
}
