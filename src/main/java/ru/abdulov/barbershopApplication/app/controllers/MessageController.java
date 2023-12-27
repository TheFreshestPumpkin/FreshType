package ru.abdulov.barbershopApplication.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.Message;
import ru.abdulov.barbershopApplication.app.entitys.Post;
import ru.abdulov.barbershopApplication.app.repositories.DialogueRepository;
import ru.abdulov.barbershopApplication.app.services.DialogueService;
import ru.abdulov.barbershopApplication.app.services.MessageService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/dialogue/{id}/sendMessage")
    public String sendMessage(@RequestParam("file1") MultipartFile file1,
                              @RequestParam("file2") MultipartFile file2,
                              @RequestParam("file3") MultipartFile file3,
                              String description,
                              @PathVariable Long id,
                              Principal principal) throws IOException {
        messageService.saveMessage(id, description, file1, file2, file3,principal);
        return "redirect:/dialogue/{id}";
    }
}
