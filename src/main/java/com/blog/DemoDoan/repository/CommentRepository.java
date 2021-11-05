package com.blog.DemoDoan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.DemoDoan.model.Comment;
import com.blog.DemoDoan.model.Post;
import com.blog.DemoDoan.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //List<Comment> findByPost(Post post);

  //  List<Comment> findAllByUser(User user);
}
