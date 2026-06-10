package com.alumni.service;

import com.alumni.entity.ForumComment;
import com.alumni.entity.ForumPost;
import com.alumni.entity.User;
import com.alumni.repository.ForumCommentRepository;
import com.alumni.repository.ForumPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    @Autowired private ForumPostRepository postRepository;
    @Autowired private ForumCommentRepository commentRepository;
    @Autowired private UserService userService;

    public ForumPost createPost(ForumPost post, String email) {
        User user = userService.getCurrentUser(email);
        post.setAuthor(user);
        return postRepository.save(post);
    }

    public List<ForumPost> getAllPosts() {
        return postRepository.findByOrderByCreatedAtDesc();
    }

    public List<ForumPost> getPostsByCategory(String category) {
        return postRepository.findByCategoryOrderByCreatedAtDesc(category);
    }

    public ForumPost getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<ForumPost> searchPosts(String keyword) {
        return postRepository.searchPosts(keyword);
    }

    public ForumComment addComment(Long postId, String content, String email) {
        User user = userService.getCurrentUser(email);
        ForumPost post = getPostById(postId);
        ForumComment comment = new ForumComment();
        comment.setContent(content);
        comment.setAuthor(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public List<ForumComment> getCommentsByPost(Long postId) {
        ForumPost post = getPostById(postId);
        return commentRepository.findByPostOrderByCreatedAtAsc(post);
    }

    public ForumPost likePost(Long postId) {
        ForumPost post = getPostById(postId);
        post.setLikesCount(post.getLikesCount() + 1);
        return postRepository.save(post);
    }

    public void deletePost(Long postId, String email) {
        ForumPost post = getPostById(postId);
        User user = userService.getCurrentUser(email);
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized");
        }
        postRepository.delete(post);
    }
}
