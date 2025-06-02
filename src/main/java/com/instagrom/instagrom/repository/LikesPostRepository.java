package com.instagrom.instagrom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagrom.instagrom.models.LikesPost;

public interface LikesPostRepository extends JpaRepository<LikesPost, Long> {
    
    LikesPost findByPostIdAndUserId(long postId, long userId);

}
