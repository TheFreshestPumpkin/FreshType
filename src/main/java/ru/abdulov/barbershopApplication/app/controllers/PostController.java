package ru.abdulov.barbershopApplication.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.Post;
import ru.abdulov.barbershopApplication.app.services.PostService;
import ru.abdulov.barbershopApplication.app.services.GroupService;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;


/** Данный класс предназначен для приёма данных о записи*/
@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final GroupService groupService;

    @PostMapping("/user/{id}/addPost")
    public String addPostFromUser(@RequestParam("file1") MultipartFile file1,
                                  @RequestParam("file2") MultipartFile file2,
                                  @RequestParam("file3") MultipartFile file3,
                                  String description,
                                  Principal principal) throws IOException {
        postService.savePostFromUser(description, file1, file2, file3, principal);
        return "redirect:/user/{id}";
    }

    @PostMapping("/group/{id}/addPost")
    public String addPostFromGroup(@RequestParam("file1") MultipartFile file1,
                                   @RequestParam("file2") MultipartFile file2,
                                   @RequestParam("file3") MultipartFile file3, String description,
                                   @PathVariable Long id, Principal principal) throws IOException {
        postService.savePostFromGroup(id, description, file1, file2, file3,principal);
        return "redirect:/group/{id}";
    }

//    @GetMapping("/post/{id}")
//    public String postInfo(@PathVariable Long id, Model model, Principal principal){
//        Post post= postService.getPostById(id);
//        if(post.getGroupAuthor()!=null){
//            model.addAttribute("author", post.getGroupAuthor());
//        }
//        else{
//            model.addAttribute("author", post.getUserAuthor());
//        }
//        model.addAttribute("comments", post.getComments());
//        model.addAttribute("pubtime", post.getPubTime());
//        model.addAttribute("posttext", post.getDescription());
//        model.addAttribute("pubTime",post.getPubTime());
//        return "post-info";
//    }

}
