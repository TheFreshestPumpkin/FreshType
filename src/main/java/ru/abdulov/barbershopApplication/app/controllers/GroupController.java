package ru.abdulov.barbershopApplication.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.Group;
import ru.abdulov.barbershopApplication.app.services.GroupService;
import ru.abdulov.barbershopApplication.app.services.UserService;

import java.io.IOException;
import java.security.Principal;

/** Данный класс обрабатывает get и post запросы, связанные с информацией о мастерах, их добавлении
 * и удалении */
@Controller
@RequiredArgsConstructor                        //встраивает необходимые аргументы
public class GroupController {
    private final GroupService groupService;  //используется final, чтобы spring сразу его заинжектил
    private final UserService userService;

    @PostMapping("/createGroup")
    public String createGroup(MultipartFile file1, String name, String description,Principal principal) throws IOException {
        groupService.saveGroup(name,description, file1, principal);
        return "redirect:/";
    }

    @GetMapping("/toCreateGroup")
    public String toCreateGroup(Principal principal,Model model) throws IOException {
        model.addAttribute("user",userService.getUserByPrincipal(principal));
        return "create-group";
    }

    @GetMapping("/group/{id}")
    public String groupInfo(@PathVariable Long id, Model model, Principal principal){
        Group group= groupService.getGroupById(id);
        model.addAttribute("group",group);
        model.addAttribute("posts",group.getPosts());
        model.addAttribute("name",group.getGroupName());
        model.addAttribute("createTime",group.getCreateTime());
        model.addAttribute("subs",group.getSubs());
        model.addAttribute("owner",group.getCreatedUser());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("isSubscribed", userService.getUserByPrincipal(principal).isSubscribed(group));
        return "group";
    }

    @GetMapping("/allGroups")
    public String listGroups(@RequestParam(name="groupName",required = false)String groupName, Principal principal, Model model) throws IOException {
        model.addAttribute("groups",groupService.listGroups(groupName));
        model.addAttribute("user",userService.getUserByPrincipal(principal));
        return "all-groups";
    }

    @PostMapping("/subscribe/{id}")
    public String subGroup(@PathVariable Long id, Principal principal) throws IOException {
        groupService.subGroup(id,principal);
        return "redirect:/group/{id}";
    }

    @PostMapping("/unsubscribe/{id}")
    public String unsubGroup(@PathVariable Long id, Principal principal) throws IOException {
        groupService.unsubGroup(id,principal);
        return "redirect:/group/{id}";
    }

}
