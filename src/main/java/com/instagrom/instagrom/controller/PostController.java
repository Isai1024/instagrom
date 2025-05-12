package com.instagrom.instagrom.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.instagrom.instagrom.config.JWTUtil;
import com.instagrom.instagrom.dto.GeneralResponse;
import com.instagrom.instagrom.dto.post.NewPost;
import com.instagrom.instagrom.dto.post.PostResponse;
import com.instagrom.instagrom.dto.post.UpdatePost;
import com.instagrom.instagrom.services.Post.PostService;
import com.smattme.requestvalidator.RequestValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private PostService postService;

    @Autowired
    private JWTUtil jwtUtil;

    private Map<String, Object> saveImage(MultipartFile file) throws IOException {
        
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return Map.of(
                "fileName", fileName,
                "fileExtension", file.getContentType());
    }

    @Operation(summary = "Create a new post", description = "Create a new post with an image and caption")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The post was created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "409", description = "Internal Unhandle Exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @PostMapping(value = "/posting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> posting(
            @RequestPart("file") MultipartFile file,
            @RequestPart NewPost newPost,
            @RequestHeader("Authorization") String token) {

        long userId = jwtUtil.getUserId(token); // * Extract user ID from the JWT token

        try {
            var fileDetails = saveImage(file); // * Save the image and get its details

            // * Set the image name and extension in the newPost object
            newPost.setImageName((String) fileDetails.get("fileName"));
            newPost.setImageExtension((String) fileDetails.get("fileExtension"));

            // * Create the post using the PostService
            long postId = postService.createPost(newPost, userId);

            var responseData = new GeneralResponse<Long>();
            responseData.setTitle("The post was created successfully");
            responseData.setMessage(String.format("The post was created with id %s", postId));
            responseData.setData(postId);
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);

        } catch (Exception e) {
            var errorData = new HashMap<String, Object>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorData);
        }
    }

    @Operation(summary = "Update a post", description = "Update a the post's caption")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The post was updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "409", description = "Internal Unhandle Exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePost(
            @RequestBody UpdatePost updatePost,
            @PathVariable("id") long id) {

        try {

            Map<String, String> rules = new HashMap<>();
            rules.put("caption", "required");

            List<String> errors = RequestValidator.validate(updatePost, rules);
            if (!errors.isEmpty()) {
                var responseData = new GeneralResponse<>();
                responseData.setTitle("Missing required parameter(s)");
                responseData.setMessage("BAD REQUEST");
                responseData.setData(errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseData);
            }

            long postId = postService.updatePost(updatePost, id);

            var responseData = new GeneralResponse<Long>();
            responseData.setTitle("The post was updated successfully");
            responseData.setMessage(String.format("The post was updated with id %s", postId));
            responseData.setData(postId);
            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch (Exception e) {
            var errorData = new HashMap<String, Object>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorData);
        }
    }

    @Operation(summary = "Delete the post information with SoftDelete", description = "Delete the post information with SoftDelete.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The post was deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "422", description = "The properties of the post are not valid.")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) throws NoSuchElementException {

        try {
            long postId = this.postService.deletePost(id); // * Delete the post using the PostService

            // * return the postId
            var responseData = new GeneralResponse<Long>();
            responseData.setTitle("The post was deleted");
            responseData.setMessage(String.format("The post was deleted with id %s", postId));
            responseData.setData(postId);
            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch (Exception e) {
            var errorData = new HashMap<String, Object>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorData);
        }

    }
    
    @Operation(summary = "Get post by ID", description = "Retrieves a post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The post was retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPost(@PathVariable Long id) {
        try {
            var response = postService.getPost(id); // * Retrieve the post using the PostService

            PostResponse postResponse = new PostResponse(response); // * Create a PostResponse object from the retrieved post
            
            return new ResponseEntity<>(postResponse, HttpStatus.OK);

        } catch (Exception e) {
            var errorData = new HashMap<String, Object>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorData);
        }
    }

    @Operation(summary = "Get all posts", description = "Retrieves a posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The posts was retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @GetMapping("")
    public ResponseEntity<Object> getPosts() {
        try {
            var response = postService.getPosts(); // * Retrieve the post using the PostService

            List<PostResponse> postResponse = response.stream()
                    .map(PostResponse::new)
                    .toList(); // * Create a PostResponse object from the retrieved post

            return new ResponseEntity<>(postResponse, HttpStatus.OK);

        } catch (Exception e) {
            var errorData = new HashMap<String, Object>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorData);
        }
    }

    @Operation(summary = "Get all posts by userId", description = "Retrieves all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The posts was retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeneralResponse.class)))
    })
    @GetMapping("/user")
    public ResponseEntity<Object> getPostsByUser(
        @RequestHeader("Authorization") String token
    ) {

        long userId = jwtUtil.getUserId(token); // * Extract user ID from the JWT token

        try {
            var response = postService.getPostsByUserId(userId); // * Retrieve the post using the PostService

            List<PostResponse> postResponse = response.stream()
                    .map(PostResponse::new)
                    .toList(); // * Create a PostResponse object from the retrieved post
                    
            return new ResponseEntity<>(postResponse, HttpStatus.OK);

        } catch (Exception e) {
            var errorData = new HashMap<String, Object>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorData);
        }
    }

}
