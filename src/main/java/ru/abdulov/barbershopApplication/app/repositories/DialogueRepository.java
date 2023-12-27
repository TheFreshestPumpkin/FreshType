package ru.abdulov.barbershopApplication.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abdulov.barbershopApplication.app.entitys.Dialogue;
import ru.abdulov.barbershopApplication.app.entitys.User;

import java.util.List;

public interface DialogueRepository extends JpaRepository<Dialogue,Long> {
    List <Dialogue> findByUsers(User user);
}
