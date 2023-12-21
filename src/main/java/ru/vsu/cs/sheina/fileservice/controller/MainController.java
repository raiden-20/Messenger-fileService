package ru.vsu.cs.sheina.fileservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.sheina.fileservice.dto.FileDTO;
import ru.vsu.cs.sheina.fileservice.service.MainService;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @DeleteMapping(value = "/file", consumes = "multipart/form-data")
    @CrossOrigin
    public ResponseEntity<?> deleteFile(@RequestPart("file") MultipartFile file,
                                        @RequestPart("fileDTO") FileDTO fileDTO,
                                        @RequestHeader("Authorization") String token) {
        mainService.deleteFile(file, fileDTO, token);
        return ResponseEntity.ok("Removal successful");
    }

    @PostMapping(value = "/file", consumes = "multipart/form-data")
    @CrossOrigin
    public ResponseEntity<?> postFile(@RequestPart("file") MultipartFile file,
                                      @RequestPart("fileDTO") FileDTO fileDTO,
                                      @RequestHeader("Authorization") String token) {
        mainService.saveFile(file, fileDTO, token);
        return ResponseEntity.ok("Save successfully");
    }
}
