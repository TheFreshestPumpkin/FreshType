package ru.abdulov.barbershopApplication.app.entitys;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.abdulov.barbershopApplication.app.entitys.enums.Role;

import java.util.*;

/** Данный класс предназначен для представления сущности пользователя */
@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "active")
    private boolean active;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password",length = 1000)
    private String password;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userAuthor")
    private List<Post>posts=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dialogue> dialogues=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> subOnGroups=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "createdUser")
    private List<Group> groups =new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
    private List<Comment> comments=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Like> likes=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
    private List<Message> messages=new ArrayList<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)           //помечается, как мапа для коллекций, Все записи хранятся в отдельной таблице
    @CollectionTable(name="user_role",joinColumns = @JoinColumn(name = "user_id")) //указывает имя таблицы, определяет оснвной столбец
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }

    public boolean isManager() {
        return roles.contains(Role.ROLE_MANAGER);
    }

    public void addSubOnGroups(Group group) {
        subOnGroups.add(group);
    }

    public void addPost(Post post) {
        post.setUserAuthor(this);
        posts.add(post);
    }

    public void addComment(Comment comment) {
        comment.setAuthor(this);
        comments.add(comment);
    }

    //от UserDetails, security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Image avatar;

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void addLike(Like like) {
        like.setOwner(this);
        likes.add(like);
    }

    public void addDialogue(Dialogue dialogue) {
        dialogue.addUsers(this);
        dialogues.add(dialogue);
    }

    public void addMessage(Message message) {
        message.setAuthor(this);
        messages.add(message);
    }

    public void addGroup(Group group) {
        group.setCreatedUser(this);
        groups.add(group);
    }

    public boolean isSubscribed(Group group){
        return subOnGroups.contains(group);
    }

    public List<Post> listSubPosts(){
        List<Post>posts1=new ArrayList<>();
        for(Group group:subOnGroups){
            posts1.addAll(group.getPosts());
        }
        return posts1;
    }

    public void addAvatar(Image image) {
        image.setUser(this);
        avatar=image;
    }
}
