package com.gest.course.repositories;


import com.gest.course.entities.Article;
import com.gest.course.entities.Category;
import com.gest.course.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IArticle extends JpaRepository<Article, Long> {

    List<Article> findByCategory(Category category);
    List<Article> findByCheckedAndUser(boolean checked, User user);
    List<Article> findByUser(User user);

    List<Article> findByChecked(boolean checked);

}
