package com.example.demo.services;

import com.example.demo.projections.CommentProjection;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.services.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentProjection> getCommentsByProduct(Long productId) {

        return commentRepository.findCommentsByProductId(productId);

    }
}
