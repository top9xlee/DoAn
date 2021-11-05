package com.blog.DemoDoan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.DemoDoan.model.Post;


public interface PostRepository extends JpaRepository<Post, Long> {

}
