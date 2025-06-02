package com.instagrom.instagrom.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagrom.instagrom.config.JWTUtil;
import com.instagrom.instagrom.dto.GeneralResponse;
import com.instagrom.instagrom.dto.comment.NewComment;
import com.instagrom.instagrom.dto.comment.UpdateComment;
import com.instagrom.instagrom.services.Comment.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JWTUtil jwtUtil;
    
    @Operation(summary = "Create a comment", description = "Send a comment to a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The comment was send", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "409", description = "Internal Unhandle Exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @PostMapping("/commenting")
    public ResponseEntity<Object> commenting(
        @RequestHeader("Authorization") String token,
        @RequestBody NewComment newComment
    ) {

        long userId = jwtUtil.getUserId(token);

        try{

            long commentId = commentService.createComment(newComment, userId);

            var responseData = new GeneralResponse<Long>();
            responseData.setTitle("The comment was created successfully");
            responseData.setMessage(String.format("The comment was created with id %s", commentId));
            responseData.setData(commentId);
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);

        }catch(Exception e){
            var errorData = new HashMap<String, Object>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorData);
        }
    }

    @Operation(summary = "Update a comment", description = "Update a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The comment was updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "409", description = "Internal Unhandle Exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateComment(
        @RequestBody UpdateComment updateComment,
        @PathVariable long id
    ) {

        try{

            long commentId = commentService.updateComment(updateComment, id);

            var responseData = new GeneralResponse<Long>();
            responseData.setTitle("The comment was updated successfully");
            responseData.setMessage(String.format("The comment was updated with id %s", commentId));
            responseData.setData(commentId);
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);

        }catch(Exception e){
            var errorData = new HashMap<String, Object>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorData);
        }
    }

    @Operation(summary = "Delete a comment", description = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The comment was deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "409", description = "Internal Unhandle Exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteComment(
        @PathVariable long id
    ) {

        try{

            long commentId = commentService.deleteComment(id);

            var responseData = new GeneralResponse<Long>();
            responseData.setTitle("The comment was deleted successfully");
            responseData.setMessage(String.format("The comment was deleted with id %s", commentId));
            responseData.setData(commentId);
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);

        }catch(Exception e){
            var errorData = new HashMap<String, Object>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorData);
        }
    }

    @Operation(summary = "Delete a comment", description = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The comment was deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "409", description = "Internal Unhandle Exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getComment(
        @PathVariable long id
    ) {

        try{

            var response = commentService.getComment(id);

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            var errorData = new HashMap<String, Object>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorData);
        }
    }

}
