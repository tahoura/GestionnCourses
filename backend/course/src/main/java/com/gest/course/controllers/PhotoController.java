package com.gest.course.controllers;

import com.gest.course.entities.Article;
import com.gest.course.entities.User;
import com.gest.course.repositories.IArticle;
import com.gest.course.repositories.IUser;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses/photos")
public class PhotoController {

    @Autowired
    private IArticle articleRepository;
    @Autowired
    private IUser userRepository;

    @GetMapping("/article/{idArticle}")
    public ResponseEntity photoArticle(@PathVariable Long idArticle) {
        if (idArticle == null) {
            return ResponseEntity.badRequest().body("Cannot get character photo with null ID");
        }
        Article article = articleRepository.getOne(idArticle);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        if (article.getPhoto() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_GIF)
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(new ByteArrayInputStream(article.getPhoto())));
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity photoUser(@PathVariable Long idUser) {
        if (idUser == null) {
            return ResponseEntity.badRequest().body("Cannot get user photo with null ID");
        }
        User user = userRepository.getOne(idUser);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (user.getPhoto() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_GIF)
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(new ByteArrayInputStream(user.getPhoto())));
    }

    @GetMapping(value = "/photoUser", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] photoAnime(Long idUser, HttpServletRequest request) {
        User user = userRepository.getOne(idUser);
        try {
            if (user.getPhoto() != null) {
                return IOUtils.toByteArray(new ByteArrayInputStream(user.getPhoto()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/photoUser")
    public byte[] photoUser(@RequestParam("myFile") MultipartFile file) throws IOException {




        byte[] ph = file.getBytes();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(ph.length);
        outputStream.write(ph, 0, ph.length);
        return outputStream.toByteArray();


    }
}