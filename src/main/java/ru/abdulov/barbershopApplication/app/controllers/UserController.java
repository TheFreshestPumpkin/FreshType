package ru.abdulov.barbershopApplication.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.User;
import ru.abdulov.barbershopApplication.app.services.UserService;

import java.io.IOException;
import java.security.Principal;

/** Данный класс предназначен для доступа к функционалу пользователя: вход, регистрация, страница пользователя*/
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model, MultipartFile file) throws IOException {
        if(!userService.createUser(user,file)){
            model.addAttribute("errorMessage","Данный email уже используется");
            return "registration";
        }
        userService.createUser(user,file);
        return "redirect:/login";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user,Principal principal, Model model){
        model.addAttribute("user1", userService.getUserByPrincipal(principal));
        model.addAttribute("posts", user.getPosts());
        model.addAttribute("user",user);
        return "user-info";
    }

    @GetMapping("/")
    public String startPage(Principal principal, Model model){
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user",user);
        model.addAttribute("posts",user.listSubPosts());
        return "startpage";
    }

    @GetMapping("/groups/{user}")
    public String groups(@PathVariable User user, Principal principal, Model model){
        User user1 = userService.getUserByPrincipal(principal);
        model.addAttribute("user",user);
        model.addAttribute("watchingUser",user1);
        model.addAttribute("subgroups",user.getSubOnGroups());
        model.addAttribute("owngroups",user.getGroups());
        return "groups";
    }

    @GetMapping("/dialogues")
    public String userDialogues(Principal principal, Model model){
        User user=userService.getUserByPrincipal(principal);
        model.addAttribute("user",user);
        model.addAttribute("dialogues",user.getDialogues());
        return "dialogues";
    }

    @GetMapping("/allUsers")
    public String listGroups(@RequestParam(name="name",required = false)String name, Principal principal, Model model) throws IOException {
        model.addAttribute("users", userService.listUsers(name,principal));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "all-users";
    }
}
