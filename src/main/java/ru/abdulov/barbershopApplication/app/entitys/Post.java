package ru.abdulov.barbershopApplication.app.entitys;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/** Данный класс предназначен для представления сущности записи */
@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "description",columnDefinition = "text")
    private String description;

    @Column(name = "pubtime")
    private Timestamp pubTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
    private List<Image> images=new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User userAuthor;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private Group groupAuthor;

    public void addImageToPost(Image image){
        image.setPost(this);
        images.add(image);
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
    private List<Like> likes=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> comments=new ArrayList<>();

    public String getPubTime(){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(pubTime);
    }

    public int likesCount(){
        return likes.size();
    }

    public void addComment(Comment comment) {
        comment.setPost(this);
        comments.add(comment);
    }

    public void addLike(Like like) {
        like.setPost(this);
        likes.add(like);
    }
}
