package ru.abdulov.barbershopApplication.app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.abdulov.barbershopApplication.app.entitys.Dialogue;
import ru.abdulov.barbershopApplication.app.entitys.Group;
import ru.abdulov.barbershopApplication.app.entitys.User;
import ru.abdulov.barbershopApplication.app.services.DialogueService;
import ru.abdulov.barbershopApplication.app.services.UserService;

import java.io.IOException;
import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DialogueController {
    private final DialogueService dialogueService;
    private final UserService userService;

    @GetMapping("/dialogue/{id}")
    public String dialogueInfo(@PathVariable Long id, Model model, Principal principal){
        Dialogue dialogue= dialogueService.getDialogueById(id);
        model.addAttribute("dialogue",dialogue);
        model.addAttribute("messages",dialogue.getMessages());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "dialogue";
    }

    @PostMapping("/dialogue/{userSender}/{user}")
    public String dialogueHandler(@PathVariable User userSender, @PathVariable User user)  throws IOException {
        Long idd=dialogueService.checkDialogue(userSender, user);
        return "redirect:/";
    }


}
