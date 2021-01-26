package com.gest.course.controllers;


import com.gest.course.entities.Article;
import com.gest.course.entities.Category;
import com.gest.course.entities.User;
import com.gest.course.repositories.IArticle;
import com.gest.course.repositories.ICategory;
import com.gest.course.repositories.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses/articles")
public class ArticleController {

    @Autowired
    private IUser userRepository;
    @Autowired
    private ICategory categoryRepository;
    @Autowired
    private IArticle articleRepository;

    @GetMapping("/")
    public ResponseEntity findAll(){

        return ResponseEntity.ok(articleRepository.findAll());
    }

    @GetMapping("/{idArticle}")
    public ResponseEntity<Article> findArticleById(@PathVariable(name = "idArticle") Long idArticle){
        Article article = articleRepository.findById(idArticle).orElse(null);
        return ResponseEntity.ok().body(article);
    }

    @PostMapping("/")
    public ResponseEntity createArticle(@RequestBody Article article){
        if(article == null){
            return ResponseEntity.badRequest().body("cannot create article with empty fields");
        }

        return ResponseEntity.ok( articleRepository.save(article));
    }

    @GetMapping("/category/{idCategory}/articles")
    public ResponseEntity findAllArticleCategory(@PathVariable Long idCategory){

        if(idCategory == null){
            return ResponseEntity.badRequest().body("cannot find category with nl user ");

        }
        Category category = categoryRepository.getOne(idCategory);

        if(category != null){

            List<Article> artCat = articleRepository.findByCategory(category);
            return ResponseEntity.ok(artCat);

        }

        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{idArticle}")
    public ResponseEntity deleteArticle(@PathVariable(name = "idArticle") Long idArticle) {
        if (idArticle == null) {
            return ResponseEntity.badRequest().body("Cannot remove article with null ID");
        }
        Article article = articleRepository.getOne(idArticle);
        if (article == null) {
            return  ResponseEntity.notFound().build();
        }
        articleRepository.delete(article);
        return ResponseEntity.ok("article removed");
    }
    @GetMapping("/article/{idArticle}")
    public ResponseEntity updateArticle(@PathVariable(name = "idArticle") Long idArticle){
        if (idArticle == null) {
            return ResponseEntity.badRequest().body("Cannot update article with null ID");
        }
        Article article = articleRepository.getOne(idArticle);

        return ResponseEntity.ok("article updated");
    }

    @GetMapping("/check/{idArticle}/{isChecked}")
    public ResponseEntity checkedArticle(@PathVariable(name = "idArticle") Long idArticle, @RequestParam(name = "isChecked", required = false) boolean isChecked) {
        if (idArticle == null) {
            return ResponseEntity.badRequest().body("Cannot remove article with null ID");
        }
        Article article = articleRepository.getOne(idArticle);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }

        article.setChecked(!isChecked);
        return ResponseEntity.ok(articleRepository.save(article));
    }
    @GetMapping("/all/{idUser}")
public ResponseEntity findAllUserArticles(@PathVariable Long idUser){
        if (idUser == null) {
            return ResponseEntity.badRequest().body("Cannot find article with null user");
        }
        User user = userRepository.getOne(idUser);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }


      return ResponseEntity.ok(articleRepository.findByUser(user));

    /* return ResponseEntity.ok(articleRepository.findByCheckedAndUser(true,user));*/
}

}
