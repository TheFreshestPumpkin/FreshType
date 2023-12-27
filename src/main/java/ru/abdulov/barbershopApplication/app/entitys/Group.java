package ru.abdulov.barbershopApplication.app.entitys;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Данный класс предназначен для представления сущности барбера */
@Data                         //Создаёт геттеры, сеттеры, toString переопределяет  equals
@Entity                      //Показывает hibernate, что данный класс эмулирует таблицу из бд
@Table(name = "pubs")     //Название таблицы
@AllArgsConstructor          //Создает конструктор который принимает в аргементы все поля класса
@NoArgsConstructor           //Создаёт конструктор без аргументов
public class Group {
    @Id                      //указывает что поле id, является id
    @GeneratedValue(strategy = GenerationType.AUTO) //автоматическая генерация id
    @Column(name = "id")                            //явное указание колонки в бд
    private Long id;

    @Column(name = "name")
    private String groupName;

    @Column(name = "descr")
    private String description;

    @Column(name = "createtime")
    private Timestamp createTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
    private Image avatar;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groupAuthor")
    private List<Post> posts = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User createdUser;

    @ManyToMany(mappedBy = "subOnGroups")
    private List<User> subs= new ArrayList<>();

    public void addSubToGroup(User user){
        subs.add(user);
    }

    public void addAvatar(Image image) {
        image.setGroup(this);
        avatar=image;
    }

    public void addPost(Post post) {
        post.setGroupAuthor(this);
        posts.add(post);
    }
}
