package ru.abdulov.barbershopApplication.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.*;
import ru.abdulov.barbershopApplication.app.repositories.CommentRepository;
import ru.abdulov.barbershopApplication.app.repositories.PostRepository;
import ru.abdulov.barbershopApplication.app.repositories.UserRepository;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void saveComment(Long id, Comment comment1, Principal principal) throws IOException {
        Timestamp sendTime = new Timestamp(System.currentTimeMillis());
        Comment comment=new Comment();
        comment.setText(comment1.getText());
        comment.setComTime(sendTime);

        Post post=postRepository.findById(id).orElseThrow();
        post.addComment(comment);

        User user = userRepository.findByEmail(principal.getName());
        user.addComment(comment);

        commentRepository.save(comment);
    }

}
