package com.gest.course.repositories;

import com.gest.course.entities.Article;
import com.gest.course.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ICategory extends JpaRepository<Category, Long> {

    @Override
    List<Category> findAll();


}
