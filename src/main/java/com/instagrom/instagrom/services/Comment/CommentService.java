package com.instagrom.instagrom.services.Comment;

import com.instagrom.instagrom.dto.comment.NewComment;
import com.instagrom.instagrom.dto.comment.UpdateComment;
import com.instagrom.instagrom.models.Comment;

public interface CommentService {

    /**
     * Creates a new comment.
     *
     * @param comment the details of the new comment
     * @param userId  the ID of the user creating the comment
     * @return the ID of the created comment
     * @throws Exception if an error occurs during comment creation
     */
    long createComment(NewComment comment, long userId) throws Exception;

    /**
     * Deletes a comment.
     *
     * @param commentId the ID of the comment to delete
     * @return the ID of the deleted comment
     * @throws Exception if an error occurs during comment deletion
     */
    long deleteComment(long commentId) throws Exception;

    /**
     * Updates an existing comment.
     *
     * @param updateComment the updated details of the comment
     * @param commentId     the ID of the comment to update
     * @return the ID of the updated comment
     * @throws Exception if an error occurs during comment update
     */
    long updateComment(UpdateComment updateComment, long commentId) throws Exception;

    /**
     * Retrieves a comment by its ID.
     *
     * @param commentId the ID of the comment to retrieve
     * @return the comment with the specified ID
     * @throws Exception if an error occurs during comment retrieval
     */
    Comment getComment(long commentId) throws Exception;

}
