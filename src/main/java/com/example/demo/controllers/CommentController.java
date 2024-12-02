package com.example.demo.controllers;

import com.example.demo.projections.CommentProjection;
import com.example.demo.services.interfaces.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/product/{productId}")
    public List<CommentProjection> getCommentsByProduct(@PathVariable Long productId) {

        return commentService.getCommentsByProduct(productId);
    }

}
