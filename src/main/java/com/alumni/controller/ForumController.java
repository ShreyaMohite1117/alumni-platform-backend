package com.alumni.controller;

import com.alumni.entity.ForumComment;
import com.alumni.entity.ForumPost;
import com.alumni.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/forum")
@CrossOrigin(origins = "*")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping("/posts")
    public ResponseEntity<List<ForumPost>> getAllPosts() {
        return ResponseEntity.ok(forumService.getAllPosts());
    }

    @GetMapping("/posts/category/{category}")
    public ResponseEntity<List<ForumPost>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(forumService.getPostsByCategory(category));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ForumPost> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(forumService.getPostById(id));
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<ForumPost>> searchPosts(@RequestParam String keyword) {
        return ResponseEntity.ok(forumService.searchPosts(keyword));
    }

    @PostMapping("/posts")
    public ResponseEntity<ForumPost> createPost(@RequestBody ForumPost post, Authentication authentication) {
        return ResponseEntity.ok(forumService.createPost(post, authentication.getName()));
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<ForumPost> likePost(@PathVariable Long id) {
        return ResponseEntity.ok(forumService.likePost(id));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Authentication authentication) {
        forumService.deletePost(id, authentication.getName());
        return ResponseEntity.ok("Post deleted");
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<ForumComment>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(forumService.getCommentsByPost(postId));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ForumComment> addComment(@PathVariable Long postId,
                                                    @RequestBody Map<String, String> body,
                                                    Authentication authentication) {
        return ResponseEntity.ok(forumService.addComment(postId, body.get("content"), authentication.getName()));
    }
}
