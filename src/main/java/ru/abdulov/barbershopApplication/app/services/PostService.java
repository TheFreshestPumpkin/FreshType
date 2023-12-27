package ru.abdulov.barbershopApplication.app.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.Group;
import ru.abdulov.barbershopApplication.app.entitys.Image;
import ru.abdulov.barbershopApplication.app.entitys.Post;
import ru.abdulov.barbershopApplication.app.entitys.User;
import ru.abdulov.barbershopApplication.app.repositories.PostRepository;
import ru.abdulov.barbershopApplication.app.repositories.GroupRepository;
import ru.abdulov.barbershopApplication.app.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;


/** Данный класс предназначен для обработки получаумых данных для взаимодействия с репозиторием записей */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /** Данный метод обрабатывает данные о записи, и сохраняет полученный объект в репозиторий */
    public void savePostFromUser(String description, MultipartFile file1, MultipartFile file2, MultipartFile file3, Principal principal) throws IOException {
        Timestamp pubTime = new Timestamp(System.currentTimeMillis());
        User user=getUserByPrincipal(principal);
        Post post=new Post();
        post.setDescription(description);
        Image image1;
        Image image2;
        Image image3;
        if(file1.getSize()!=0){
            image1 = toImageEntity(file1);
            post.addImageToPost(image1);
        }
        if(file2.getSize()!=0){
            image2 = toImageEntity(file2);
            post.addImageToPost(image2);
        }
        if(file3.getSize()!=0) {
            image3 = toImageEntity(file3);
            post.addImageToPost(image3);
        }
        post.setPubTime(pubTime);
        user.addPost(post);
        postRepository.save(post);
    }

    private Image toImageEntity(MultipartFile file) throws IOException{
        Image image=new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void savePostFromGroup(Long id,String description, MultipartFile file1, MultipartFile file2,
                                 MultipartFile file3,Principal principal) throws IOException {
        User user=getUserByPrincipal(principal);
        if(user.equals(groupRepository.findById(id).orElseThrow().getCreatedUser())){
            Timestamp pubTime = new Timestamp(System.currentTimeMillis());
            Post post=new Post();
            post.setDescription(description);
            Image image1;
            Image image2;
            Image image3;
            if(file1.getSize()!=0){
                image1 = toImageEntity(file1);
                post.addImageToPost(image1);
            }
            if(file2.getSize()!=0){
                image2 = toImageEntity(file2);
                post.addImageToPost(image2);
            }
            if(file3.getSize()!=0) {
                image3 = toImageEntity(file3);
                post.addImageToPost(image3);
            }
            Group group=groupRepository.findById(id).orElseThrow();
            post.setPubTime(pubTime);
            post.setUserAuthor(null);
            group.addPost(post);
            postRepository.save(post);
        }

    }

    /** Данный метод позволяет получить пользовтеля по сущности principal(данным уже вошедшего пользователя) */
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
}
