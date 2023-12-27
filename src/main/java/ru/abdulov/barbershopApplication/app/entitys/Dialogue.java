package ru.abdulov.barbershopApplication.app.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/** Данный класс предназначен для представления сущности записи */
@Entity
@Table(name = "dialogues")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dialogue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "active")
    private boolean active;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<User> users=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dialogue")
    private List<Message> messages=new ArrayList<>();

    public void addUsers(User user) {
        users.add(user);
    }

    public void addMessage(Message message) {
        message.setDialogue(this);
        messages.add(message);
    }
}