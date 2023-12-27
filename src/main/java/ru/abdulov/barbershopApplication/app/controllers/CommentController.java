package ru.abdulov.barbershopApplication.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.abdulov.barbershopApplication.app.entitys.Comment;
import ru.abdulov.barbershopApplication.app.services.CommentService;

import java.io.IOException;
import java.security.Principal;

@Controller

@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{userId}/{id}/addComment")
    public String addComment(@PathVariable Long id,@PathVariable Long userId, Principal principal, Comment comment) throws IOException{
        commentService.saveComment(id,comment,principal);
        return "redirect:/user/{userId}";
    }
}
