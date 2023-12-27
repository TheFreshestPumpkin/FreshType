package ru.abdulov.barbershopApplication.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.Dialogue;
import ru.abdulov.barbershopApplication.app.entitys.Image;
import ru.abdulov.barbershopApplication.app.entitys.Message;
import ru.abdulov.barbershopApplication.app.entitys.User;
import ru.abdulov.barbershopApplication.app.repositories.DialogueRepository;
import ru.abdulov.barbershopApplication.app.repositories.MessageRepository;
import ru.abdulov.barbershopApplication.app.repositories.UserRepository;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final DialogueRepository dialogueRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public void saveMessage(Long id, String text, MultipartFile file1, MultipartFile file2, MultipartFile file3, Principal principal) throws IOException {
        Timestamp sendTime = new Timestamp(System.currentTimeMillis());
        Message message=new Message();
        message.setText(text);
        Image image1;
        Image image2;
        Image image3;
        if(file1.getSize()!=0){
            image1=new Image(file1);
            message.addImageToMessage(image1);
        }
        if(file2.getSize()!=0){
            image2=new Image(file2);
            message.addImageToMessage(image2);
        }
        if(file3.getSize()!=0) {
            image3=new Image(file3);
            message.addImageToMessage(image3);
        }
        message.setMesTime(sendTime);
        Dialogue dialogue=dialogueRepository.findById(id).orElseThrow();
        dialogue.addMessage(message);
        User user=userRepository.findByEmail(principal.getName());
        user.addMessage(message);
        messageRepository.save(message);
    }
}
