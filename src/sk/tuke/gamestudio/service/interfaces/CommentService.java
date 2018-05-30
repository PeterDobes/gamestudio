package sk.tuke.gamestudio.service.interfaces;

import sk.tuke.gamestudio.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);

    List<Comment> getAllComments(String game);
}
