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
import com.instagrom.instagrom.models.LikesPost;
import com.instagrom.instagrom.models.Post;
import com.instagrom.instagrom.models.User;
import com.instagrom.instagrom.repository.LikesPostRepository;
import com.instagrom.instagrom.repository.PostRepository;
import com.instagrom.instagrom.repository.UserRepository;

@Service
public class MockPostService implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikesPostRepository likesPostRepository;

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
            return postRepository.findByUserIdOrderById(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving posts by user ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Post> getPosts() {
        try {
            return postRepository.findAllByOrderById();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving posts: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean likePost(long postId, long userId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        try {
            
            LikesPost likePost = likesPostRepository.findByPostIdAndUserId(postId, userId);
            if (likePost != null) {
                // If the user already liked the post, remove the like
                likesPostRepository.delete(likePost);
                post.setLikesCount(post.getLikesCount() - 1);
                postRepository.saveAndFlush(post);
                return false; // Return false to indicate the post was unliked
            }else {
                // If the user has not liked the post, add a new like
                likePost = new LikesPost();
            }

            likePost.setPost(post);
            likePost.setUser(user);
            likePost.setLiked(true);

            post.setLikesCount(post.getLikesCount() + 1);

            likesPostRepository.saveAndFlush(likePost);

            return true; // Return true to indicate the post was liked

        } catch (Exception e) {
            throw new RuntimeException("Error liking post: " + e.getMessage(), e);
        }
    }

}
