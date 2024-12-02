package com.example.demo.repositories;

import com.example.demo.models.Comment;
import com.example.demo.projections.CommentProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    @Query("SELECT c.comment AS comment, c.date AS date, cu.name AS authorName " +
            "FROM Comment c JOIN c.customer cu WHERE c.product.id = :productId")
    List<CommentProjection> findCommentsByProductId(@Param("productId") Long productId);
}
