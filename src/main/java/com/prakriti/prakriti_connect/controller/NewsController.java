package com.prakriti.prakriti_connect.controller;

import com.prakriti.prakriti_connect.dto.CommentDto;
import com.prakriti.prakriti_connect.model.Comments;
import com.prakriti.prakriti_connect.model.News;
import com.prakriti.prakriti_connect.model.User;
import com.prakriti.prakriti_connect.repositories.CommentRepo;
import com.prakriti.prakriti_connect.repositories.NewsRepo;
import com.prakriti.prakriti_connect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class NewsController {
    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/news")
    public List<News> getAllNews() {
        return newsRepo.findAll();
    }

    @GetMapping("/news/{id}/comments")
    public List<CommentDto> getCommentsByNews(@PathVariable int id) {

        List<Comments> comments = commentRepo.findByNewsIdOrderByCommentDateAsc(id);
        List<CommentDto> result = new ArrayList<>();

        for (Comments c : comments) {
            CommentDto dto = new CommentDto();

            User user = userRepo.findById(c.getUserId()).orElse(null);

            dto.setUsername(user != null ? user.getUsername() : "Unknown");
            dto.setCommentText(c.getCommentText());
            dto.setCommentDate(c.getCommentDate());

            result.add(dto);
        }

        return result;
    }

    @PostMapping("/news/{id}/comment")
    public Comments addComment(
            @PathVariable int id,
            @RequestBody Comments comment
    ) {
        comment.setNewsId(id);
        return commentRepo.save(comment);
    }

}