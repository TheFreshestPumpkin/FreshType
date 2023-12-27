package ru.abdulov.barbershopApplication.app.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.Group;
import ru.abdulov.barbershopApplication.app.entitys.Image;
import ru.abdulov.barbershopApplication.app.entitys.User;
import ru.abdulov.barbershopApplication.app.repositories.GroupRepository;
import ru.abdulov.barbershopApplication.app.repositories.UserRepository;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

/** Данный класс предназначен для обработки получаумых данных для взаимодействия с репозиторием барберов */
@Service
@RequiredArgsConstructor
@Slf4j                      //Система протоколирования, используем для логирования
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;


    /** Данный метод заполняет поля объекта барбера и сохраяент его в репозиторий */
    public void saveGroup(String name, String description, MultipartFile file, Principal principal) throws IOException{
        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        Group group=new Group();
        group.setGroupName(name);
        group.setDescription(description);
        group.setCreateTime(createdTime);
        Image image;
        if(file.getSize()!=0){
            image=new Image(file);
            group.addAvatar(image);
        }
        User user = userRepository.findByEmail(principal.getName());
        user.addGroup(group);
        groupRepository.save(group);
    }

    public List<Group> listGroups(String groupName) {
        if (groupName != null) return groupRepository.findByGroupName(groupName);
        return groupRepository.findAll();
    }

    /** Данный метод позволяет удалить из репозитория бербера по id */
    public void deleteGroup(Long id){
        groupRepository.deleteById(id);
    }

    /** Данный метод позволяет получить объект барбера из репозитория по id */
    public Group getGroupById(Long id){
        return groupRepository.findById(id).orElse(null);

    }

    public void subGroup(Long id, Principal principal) {
        Group group=groupRepository.findById(id).orElseThrow();
        User user=userRepository.findByEmail(principal.getName());
        group.addSubToGroup(user);
        user.addSubOnGroups(group);
        groupRepository.save(group);
        userRepository.save(user);
    }

    public void unsubGroup(Long id, Principal principal) {
        Group group=groupRepository.findById(id).orElseThrow();
        User user=userRepository.findByEmail(principal.getName());
        group.getSubs().remove(user);
        user.getSubOnGroups().remove(group);
        groupRepository.save(group);
        userRepository.save(user);
    }
}
