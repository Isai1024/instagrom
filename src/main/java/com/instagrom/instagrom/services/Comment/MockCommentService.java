package com.instagrom.instagrom.services.Comment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagrom.instagrom.dto.comment.NewComment;
import com.instagrom.instagrom.dto.comment.UpdateComment;
import com.instagrom.instagrom.models.Comment;
import com.instagrom.instagrom.models.Post;
import com.instagrom.instagrom.models.User;
import com.instagrom.instagrom.repository.CommentRepository;
import com.instagrom.instagrom.repository.PostRepository;
import com.instagrom.instagrom.repository.UserRepository;

@Service
public class MockCommentService implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public long createComment(NewComment comment, long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User no found."));
        Post post = postRepository.findById(comment.getPostId()).orElseThrow(() -> new NoSuchElementException("Post no found."));

        try{

            // Retrieve the current date and time to set as the creation date for the comment
            Instant instant = LocalDateTime.now().toInstant(ZoneOffset.of("-06:00"));
            Date CreationDate = Date.from(instant);

            // increment the comments count for the post
            post.setCommentsCount(post.getCommentsCount() + 1);
            postRepository.saveAndFlush(post);

            // Create a new comment object and set its properties
            Comment newComment = new Comment();
            newComment.setText(comment.getText());
            newComment.setUser(user);
            newComment.setPost(post);
            newComment.setCreatedAt(CreationDate);
            newComment.setUpdatedAt(CreationDate);

            commentRepository.saveAndFlush(newComment);

            return newComment.getId();

        }catch(Exception e){
            throw new RuntimeException("Error creating comment: " + e.getMessage(), e);
        }
    }

    @Override
    public long deleteComment(long commentId) throws Exception {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("Comment no found."));
        Post post = comment.getPost();

        try{

            Instant instant = LocalDateTime.now().toInstant(ZoneOffset.of("-06:00"));
            Date deleteDate = Date.from(instant);

            // decrement the comments count for the post
            post.setCommentsCount(post.getCommentsCount() - 1);
            postRepository.saveAndFlush(post);

            comment.setDeletedAt(deleteDate);

            commentRepository.saveAndFlush(comment);

            return comment.getId();

        }catch(Exception e){
            throw new RuntimeException("Error deleting comment: " + e.getMessage(), e);
        }
    }

    @Override
    public long updateComment(UpdateComment updateComment, long commentId) throws Exception {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("Comment no found."));

        try{

            Instant instant = LocalDateTime.now().toInstant(ZoneOffset.of("-06:00"));
            Date updateDate = Date.from(instant);

            comment.setText(updateComment.getText());
            comment.setUpdatedAt(updateDate);

            commentRepository.saveAndFlush(comment);

            return comment.getId();

        }catch(Exception e){
            throw new RuntimeException("Error updating comment: " + e.getMessage(), e);
        }
    }

    @Override
    public Comment getComment(long commentId) throws Exception {
        try {
            return commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("Comment not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving comment: " + e.getMessage(), e);
        }
    }
    
}
