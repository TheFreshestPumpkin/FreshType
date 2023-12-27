package ru.abdulov.barbershopApplication.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abdulov.barbershopApplication.app.entitys.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
