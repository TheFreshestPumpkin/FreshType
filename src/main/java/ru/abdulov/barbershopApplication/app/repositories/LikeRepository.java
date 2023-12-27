package ru.abdulov.barbershopApplication.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.abdulov.barbershopApplication.app.entitys.Like;
import ru.abdulov.barbershopApplication.app.entitys.Post;
import ru.abdulov.barbershopApplication.app.entitys.User;

import java.util.List;


public interface LikeRepository extends JpaRepository<Like,Long> {
    Like findByOwnerAndPost(User owner, Post post);
}
