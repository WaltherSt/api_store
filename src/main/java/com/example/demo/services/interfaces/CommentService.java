package com.example.demo.services.interfaces;

import com.example.demo.projections.CommentProjection;

import java.util.List;

public interface CommentService {
 List<CommentProjection> getCommentsByProduct(Long productId);


}
