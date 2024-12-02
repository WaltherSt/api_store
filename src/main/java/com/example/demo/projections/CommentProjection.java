package com.example.demo.projections;

public interface CommentProjection {
    Long getCommentId(); // Este debe coincidir con el alias "commentId" de la consulta
    String getComment(); // Alias "comment"
    String getDate(); // Alias "date"
    String getAuthorName(); // Alias "authorName"
}