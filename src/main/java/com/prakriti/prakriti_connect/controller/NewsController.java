package com.prakriti.prakriti_connect.controller;

import com.prakriti.prakriti_connect.dto.CommentDto;
import com.prakriti.prakriti_connect.model.Comments;
import com.prakriti.prakriti_connect.model.News;
import com.prakriti.prakriti_connect.model.Notification;
import com.prakriti.prakriti_connect.model.User;
import com.prakriti.prakriti_connect.repositories.CommentRepo;
import com.prakriti.prakriti_connect.repositories.NewsRepo;
import com.prakriti.prakriti_connect.repositories.NotificationRepo;
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

    @Autowired
    NotificationRepo notificationRepo;

    @GetMapping("/news")
    public List<News> getAllNews() {
        return newsRepo.findAll();
    }

    @GetMapping("/news/{id}")
    public News getNewsById(@PathVariable int id) {
        return newsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));
    }

    @PutMapping("/news/{id}")
    public News updateNews(@PathVariable int id, @RequestBody News updatedNews) {

        News news = newsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found"));

        news.setTitle(updatedNews.getTitle());
        news.setDescription(updatedNews.getDescription());

        return newsRepo.save(news);
    }

    @DeleteMapping("/news/{id}")
    public void deleteNews(@PathVariable int id) {
        if(newsRepo.existsById(id)){
            newsRepo.deleteById(id);
        }
        else{
            throw new RuntimeException("News not found");
        }
    }

    @PostMapping("/news")
    public String addNews(@RequestBody News news) {

        if (news.getTitle() == null || news.getTitle().isEmpty()
                || news.getDescription() == null || news.getDescription().isEmpty()) {
            return "Title and description are required";
        }
        newsRepo.save(news);

        List<User> users = userRepo.findAll();

        for (User u : users) {
            Notification n = new Notification();
            n.setUserId(u.getId());
            n.setMessage("📰 New article posted by admin: " + news.getTitle());
            n.setType("NEWS");
            n.setRead(false);

            notificationRepo.save(n);
        }
        return "News Added Successfully";
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