package com.instagrom.instagrom.services.Post;

import java.util.List;

import com.instagrom.instagrom.dto.post.NewPost;
import com.instagrom.instagrom.dto.post.UpdatePost;
import com.instagrom.instagrom.models.Post;

public interface PostService {
  
    /**
     * Creates a new post.
     *
     * @param newPost the details of the new post
     * @param userId  the ID of the user creating the post
     * @return the ID of the created post
     */
    long createPost(NewPost newPost, long userId);

    /**
     * Updates an existing post.
     *
     * @param updatePost the updated details of the post
     * @param postId     the ID of the post to update
     * @return the ID of the updated post
     */
    long updatePost(UpdatePost updatePost, long postId);

    /**
     * Deletes a post.
     *
     * @param postId the ID of the post to delete
     * @return the ID of the deleted post
     */
    long deletePost(long postId);

    /**
     * Retrieves a post by its ID.
     *
     * @param postId the ID of the post to retrieve
     * @return the post with the specified ID
     */
    Post getPost(long postId);

    /**
     * Retrieves all posts by a specific user.
     *
     * @param userId the ID of the user whose posts to retrieve
     * @return a list of posts by the specified user
     */
    List<Post> getPostsByUserId(long userId);

    /**
     * Retrieves all posts.
     *
     * @return a list of all posts
     */
    List<Post> getPosts();

    /**
     * Likes a post.
     * 
     * @param postId
     * @param userId
     * @return the ID of the post that was liked
     */
    boolean likePost(long postId, long userId);

}
