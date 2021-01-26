package com.gest.course.repositories;


import com.gest.course.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUser extends JpaRepository<User, Long> {

    User findByMailAndPassword(String mail, String password);

User findById(long idUser);

}
