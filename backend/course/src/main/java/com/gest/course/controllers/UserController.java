package com.gest.course.controllers;

import com.gest.course.entities.Article;
import com.gest.course.entities.Category;
import com.gest.course.entities.User;
import com.gest.course.repositories.IArticle;
import com.gest.course.repositories.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses/users")

public class UserController {

    @Autowired
    private IUser userRepository;
    @Autowired
    private IArticle articleRepository;


    @GetMapping("/")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<User> findUserById(@PathVariable(name= "idUser") Long idUser){

        User user = userRepository.findById(idUser).orElse(null);
        return ResponseEntity.ok().body(user);
    }
  /*  @PostMapping(value="/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createUser(@RequestPart("user") User user, @RequestPart("file") MultipartFile file)  {

        if(user == null){
            return ResponseEntity.badRequest().body("cannot create user with empty fields");
        }
        byte[] ph = file.getBytes();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(ph.length);
        outputStream.write(ph, 0, ph.length);
        user.setPhoto(ph);
        User createdUser = userRepository.save(user);


        return ResponseEntity.ok(createdUser);
    }
    */
  @PostMapping(value="/new",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createUser(@RequestPart("file") MultipartFile file,  @RequestPart User user) throws IOException {
        if(file.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
//Il faut récupérer l'utilisateur ou l'entite de la BD
       /* User user= userRepository.findById(idUser).get();
        if (user == null) {
            return "No user were foud with the specefic ID";
        }*/
        user.setPhoto(file.getBytes());
        userRepository.save(user);
      return ResponseEntity.ok("user insert");
    }

    @PostMapping("/login")
    public  ResponseEntity login(@RequestParam(name = "mail") String mail, @RequestParam(name = "password") String password){

        if(StringUtils.isEmpty(mail) || StringUtils.isEmpty(password)){
            return ResponseEntity.badRequest().body("cannot login with empty user login or password");
        }

        User authentificatedUser = userRepository.findByMailAndPassword(mail, password);
        if(authentificatedUser == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authentificatedUser);
    }
    @PutMapping("/{idUser}")
    public ResponseEntity updateUser(@PathVariable(name= "idUser") Long idUser, @Valid @RequestBody User  user) {
        if (!userRepository.findById(idUser).isPresent()) {
            return ResponseEntity.badRequest().body("cannot find user with ID");
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/delete/{idUser}")
    public ResponseEntity deleteUser(@PathVariable(name = "idUser") Long idUser) {
        if (idUser == null) {
            return ResponseEntity.badRequest().body("Cannot remove character with null ID");
        }
        User user = userRepository.getOne(idUser);
        if (user == null) {
            return  ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.ok("User removed with success");
    }
    @GetMapping("/article/{idUser}")
    public ResponseEntity findArticleByUser(@PathVariable Long idUser){
        if(idUser == null){
            return ResponseEntity.badRequest().body("cannot find category with nl user ");

        }
        User user = userRepository.getOne(idUser);

        if(user != null){

            List<Article> artUse = articleRepository.findByUser(user);
            return ResponseEntity.ok(artUse);

        }

        return ResponseEntity.notFound().build();

    }
    @PostMapping("/article/{idUser}/articles")
    public ResponseEntity createArticleUser(@RequestBody User user){

        if(user == null){
            return ResponseEntity.badRequest().body("cannot create user with empty fields");
        }
        User createdUser = userRepository.save(user);
        return ResponseEntity.ok(createdUser);
    }


}
