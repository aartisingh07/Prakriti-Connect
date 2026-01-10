package com.prakriti.prakriti_connect.repositories;

import com.prakriti.prakriti_connect.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comments,Integer> {
    List<Comments> findByNewsIdOrderByCommentDateAsc(int newsId);

}
