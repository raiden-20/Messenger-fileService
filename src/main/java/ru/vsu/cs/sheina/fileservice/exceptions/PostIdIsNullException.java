package ru.vsu.cs.sheina.fileservice.exceptions;

import org.springframework.http.HttpStatus;

public class PostIdIsNullException extends AppException {
    public PostIdIsNullException() {
        super("Post id is null", HttpStatus.BAD_REQUEST);
    }
}
