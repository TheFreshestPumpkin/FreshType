package ru.abdulov.barbershopApplication.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.abdulov.barbershopApplication.app.entitys.Like;
import ru.abdulov.barbershopApplication.app.entitys.Post;
import ru.abdulov.barbershopApplication.app.entitys.User;
import ru.abdulov.barbershopApplication.app.repositories.LikeRepository;
import ru.abdulov.barbershopApplication.app.repositories.PostRepository;
import ru.abdulov.barbershopApplication.app.repositories.UserRepository;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    public void changeLike(Principal principal, Long id) {
        User user=userRepository.findByEmail(principal.getName());
        Post post=postRepository.findById(id).orElseThrow();
        Like isLike=likeRepository.findByOwnerAndPost(user,post);
        if(isLike==null){
            Like like=new Like();

            user.addLike(like);
            post.addLike(like);
            likeRepository.save(like);
        }
        else{
            likeRepository.delete(isLike);
        }
    }
}
