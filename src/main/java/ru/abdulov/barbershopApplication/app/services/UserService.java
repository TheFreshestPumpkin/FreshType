package ru.abdulov.barbershopApplication.app.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.abdulov.barbershopApplication.app.entitys.Group;
import ru.abdulov.barbershopApplication.app.entitys.Image;
import ru.abdulov.barbershopApplication.app.entitys.User;
import ru.abdulov.barbershopApplication.app.entitys.enums.Role;
import ru.abdulov.barbershopApplication.app.repositories.GroupRepository;
import ru.abdulov.barbershopApplication.app.repositories.UserRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/** Данный класс предназначен для обработки получаумых данных для взаимодействия с репозиторием пользователей */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;

    /** Данный метод создаёт объект класса user по полученным данным и сохраняет его в репозиторий */
    public boolean createUser(User user, MultipartFile file) throws IOException {
        String email = user.getEmail();
        if(userRepository.findByEmail(email) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        Image image;
        if(file!=null){
            image=new Image(file);
            user.addAvatar(image);
        }
        userRepository.save(user);
        return true;
    }

    public List<User> listUsers(String name, Principal principal) {
        if (name != null){
            List<User>users=userRepository.findByName(name);
            users.remove(userRepository.findByEmail(principal.getName()));
            return users;
        }
        return userRepository.findAll();
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    /** Данный метод позволяет получить из репозитория список всех пользователей */
    public List<User> userList(){
        return userRepository.findAll();
    }

    /** Данный метод позволяет удалить пользователя из репозитория по id */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /** Данный метод позвоялет поменять роли полученного пользователя */
    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }


}
