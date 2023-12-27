package ru.abdulov.barbershopApplication.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.abdulov.barbershopApplication.app.entitys.Comment;
import ru.abdulov.barbershopApplication.app.entitys.User;
import ru.abdulov.barbershopApplication.app.services.LikeService;
import ru.abdulov.barbershopApplication.app.services.UserService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/{idU}/{idP}/changeLike")
    public String changeLike(@PathVariable Long idP, @PathVariable Long idU, Principal principal) throws IOException {
        likeService.changeLike(principal,idP);
        return "redirect:/user/{idU}";
    }
}
