package ru.abdulov.barbershopApplication.app.entitys;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/** Данный класс предназначен для представления сущности изображения */
@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "originalFileName")
    private String originalFileName;

    @Column(name = "size")
    private Long size;

    @Column(name = "contentType")
    private String contentType;

    @OneToOne(fetch = FetchType.LAZY)
    private Group group;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] bytes;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER) //Показывает отношение таблиц barbers и images, много фотографий к 1 барберу
    private Post post;                                           //cascade - как изменение фотографии повлияет на сущность барбера(обновится), fetch - способ загрузки, eager - подгружает все связанные объекты сразу

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Message message;

    public Image(MultipartFile file) throws IOException {
        name=file.getName();
        originalFileName=file.getOriginalFilename();
        contentType=file.getContentType();
        size=file.getSize();
        bytes=file.getBytes();
    }
}
