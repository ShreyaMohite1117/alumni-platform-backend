package com.alumni.repository;

import com.alumni.entity.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {

    List<ForumPost> findByOrderByCreatedAtDesc();

    List<ForumPost> findByCategoryOrderByCreatedAtDesc(String category);

    @Query("SELECT p FROM ForumPost p WHERE " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "ORDER BY p.createdAt DESC")
    List<ForumPost> searchPosts(@Param("keyword") String keyword);
}
