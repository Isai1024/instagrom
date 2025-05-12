package com.instagrom.instagrom.services.Post;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagrom.instagrom.dto.post.NewPost;
import com.instagrom.instagrom.dto.post.UpdatePost;
import com.instagrom.instagrom.models.Post;
import com.instagrom.instagrom.models.User;
import com.instagrom.instagrom.repository.PostRepository;
import com.instagrom.instagrom.repository.UserRepository;

@Service
public class MockPostService implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public long createPost(NewPost newPost, long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        try {
            Instant instant = LocalDateTime.now().toInstant(ZoneOffset.of("-06:00"));
            Date CreationDate = Date.from(instant);

            Post post = new Post();
            post.setCaption(newPost.getCaption());
            post.setImageName(newPost.getImageName());
            post.setImageExtension(newPost.getImageExtension());
            post.setUser(user);
            post.setCommentsCount(0);
            post.setLikesCount(0);
            post.setCreatedAt(CreationDate);
            post.setUpdatedAt(CreationDate);

            postRepository.saveAndFlush(post); 

            return post.getId();

        } catch (Exception e) {
            throw new RuntimeException("Error creating post: " + e.getMessage(), e);
        }
    }

    @Override
    public long updatePost(UpdatePost updatePost, long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));
        try {
            Instant instant = LocalDateTime.now().toInstant(ZoneOffset.of("-06:00"));
            Date updateDate = Date.from(instant);

            post.setCaption(updatePost.getCaption());
            post.setUpdatedAt(updateDate);

            postRepository.saveAndFlush(post); 

            return post.getId();

        } catch (Exception e) {
            throw new RuntimeException("Error updating post: " + e.getMessage(), e);
        }
    }

    @Override
    public long deletePost(long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));
        try {
            Instant instant = LocalDateTime.now().toInstant(ZoneOffset.of("-06:00"));
            Date deleteDate = Date.from(instant);

            post.setDeletedAt(deleteDate);
            postRepository.saveAndFlush(post); 

            return post.getId();

        } catch (Exception e) {
            throw new RuntimeException("Error deleting post: " + e.getMessage(), e);
        }
    }

    @Override
    public Post getPost(long postId) {
        try {
            return postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving post: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Post> getPostsByUserId(long userId) {
        try {
            return postRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving posts by user ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Post> getPosts() {
        try {
            return postRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving posts: " + e.getMessage(), e);
        }
    }

    @Override
    public long likePost(long postId, long userId) {

        /**
        * * TODO Auto-generated method stub
        * FUNCIONALIDAD DE LIKE
        * RESPONSE PARA LIKESPOST
        */

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'likePost'");
    }
    
}
