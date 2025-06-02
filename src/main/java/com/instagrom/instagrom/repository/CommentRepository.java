package com.instagrom.instagrom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagrom.instagrom.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
