package ru.abdulov.barbershopApplication.app.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.abdulov.barbershopApplication.app.entitys.Dialogue;
import ru.abdulov.barbershopApplication.app.entitys.Group;
import ru.abdulov.barbershopApplication.app.entitys.User;
import ru.abdulov.barbershopApplication.app.repositories.DialogueRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DialogueService {
    private final DialogueRepository dialogueRepository;

    public Dialogue getDialogueById(Long id){
        return dialogueRepository.findById(id).orElse(null);

    }

//    public Dialogue addDialogue(User userSender, User user) {
//        Dialogue dialogue=new Dialogue();
//        dialogue.setActive(true);
//        dialogue.setName("Дилог "+userSender.getName()+" и "+user.getName());
//        userSender.addDialogue(dialogue);
//        user.addDialogue(dialogue);
//        dialogueRepository.save(dialogue);
//        return ;
//    }

    public Long checkDialogue(User userSender, User user) {
        List<Dialogue>dialogues1=dialogueRepository.findByUsers(userSender);
        List<Dialogue>dialogues2=dialogueRepository.findByUsers(user);
        List<Dialogue>result=new ArrayList<>(dialogues1);
        result.retainAll(dialogues2);
        if(result.isEmpty()){
            Dialogue dialogue=new Dialogue();
            dialogue.setActive(true);
            dialogue.setName("Дилог "+userSender.getName()+" и "+user.getName());
            userSender.addDialogue(dialogue);
            user.addDialogue(dialogue);
            log.info("returning value from new"+dialogueRepository.save(dialogue).getId());
            return dialogueRepository.save(dialogue).getId();
        }
        return result.get(0).getId();
    }
}
