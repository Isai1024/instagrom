package com.instagrom.instagrom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagrom.instagrom.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserIdOrderById(long userId);

    List<Post> findAllByOrderById();

}
