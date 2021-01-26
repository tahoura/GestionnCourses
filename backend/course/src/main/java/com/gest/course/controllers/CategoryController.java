package com.gest.course.controllers;

import com.gest.course.entities.Article;
import com.gest.course.entities.Category;

import com.gest.course.repositories.IArticle;
import com.gest.course.repositories.ICategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses/category")
public class CategoryController {

    @Autowired
    private ICategory categoryRepository;
    @Autowired
    private IArticle articleRepository;



    @GetMapping("/")
    public ResponseEntity findAll(){

        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<Category> findCategoryById(@PathVariable(name = "idCategory") Long idCategory){
        Category category = categoryRepository.findById(idCategory).orElse(null);
        return ResponseEntity.ok().body(category);
    }
    @PostMapping("/")
    public ResponseEntity createCategory(@RequestBody Category category){
        if(category == null){
            return ResponseEntity.badRequest().body("cannot create category with empty fields");
        }
        return ResponseEntity.ok(categoryRepository.save(category));
    }
    @DeleteMapping("/delete/{idCategory}")
    public ResponseEntity deleteCategory(@PathVariable(name = "idCategory") Long idCategory){
        if(idCategory == null){
            return ResponseEntity.badRequest().body("cannot remove category with null ID");
        }
        Category category = categoryRepository.getOne(idCategory);
        if(category == null){
            return ResponseEntity.notFound().build();
        }
        categoryRepository.delete(category);
        return ResponseEntity.ok("category removed with success");
    }


}
