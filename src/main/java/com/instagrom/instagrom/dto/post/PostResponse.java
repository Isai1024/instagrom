package com.instagrom.instagrom.dto.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.instagrom.instagrom.dto.comment.CommentResponse;
import com.instagrom.instagrom.dto.user.UserResponse;
import com.instagrom.instagrom.models.Comment;
import com.instagrom.instagrom.models.Post;
import com.instagrom.instagrom.models.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostResponse {
    
    private long id;
    private String imageName;
    private String imageExtension;
    private String caption;
    private List<Comment> comments;
    private int commentsCount;
    private int likesCount;
    private User user;
    private Date createdAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.imageName = post.getImageName();
        this.imageExtension = post.getImageExtension();
        this.caption = post.getCaption();
        this.comments = post.getComment();
        this.commentsCount = post.getCommentsCount();
        this.likesCount = post.getLikesCount();
        this.user = post.getUser();
        this.createdAt = post.getCreatedAt();
    }

    public PostResponse() {}

    public UserResponse getUser() {
        return new UserResponse(user);
    }

    public List<CommentResponse> getComments() {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(new CommentResponse(comment));
        }
        return commentResponses;
    }

}
