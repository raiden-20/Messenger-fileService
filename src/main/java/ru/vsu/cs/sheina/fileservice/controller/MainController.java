package ru.vsu.cs.sheina.fileservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.sheina.fileservice.dto.BlogUrlDTO;
import ru.vsu.cs.sheina.fileservice.dto.FileSocialDTO;
import ru.vsu.cs.sheina.fileservice.service.MainService;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @PostMapping(value = "/file/social", consumes = "multipart/form-data")
    @CrossOrigin
    public ResponseEntity<?> postSocial(@RequestPart("file") MultipartFile file,
                                        @RequestPart("url") String url,
                                        @RequestPart("source") String source,
                                        @RequestHeader("Authorization") String token) {
        //mainService.postSocialFile(file, url, source, token);
        return ResponseEntity.ok("Save successfully");
    }

    @PostMapping(value = "/file/blog", consumes = "multipart/form-data")
    @CrossOrigin
    public ResponseEntity<?> postFile(@RequestPart("file") MultipartFile file,
                                      @RequestPart("postId") String postId) {
        mainService.postBlogFile(file, Integer.getInteger(postId));
        return ResponseEntity.ok("Save successfully");
    }

    @DeleteMapping("/file/social")
    @CrossOrigin
    public ResponseEntity<?> deleteSocial(@RequestBody FileSocialDTO socialDTO,
                                          @RequestHeader("Authorization") String token) {
        mainService.deleteSocialFile(socialDTO, token);
        return ResponseEntity.ok("Removal successful");
    }

    @DeleteMapping( "/file/blog")
    @CrossOrigin
    public ResponseEntity<?> deleteBlog(@RequestBody BlogUrlDTO blogDTO,
                                        @RequestHeader("Authorization") String token) {
        mainService.deleteBlogFile(blogDTO, token);
        return ResponseEntity.ok("Removal successful");
    }
}
