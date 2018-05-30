package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.interfaces.CommentService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CommentServiceImplJPA implements CommentService {
    @PersistenceContext
    private EntityManager entityManager;

    private static Comment myComment = null;

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getAllComments(String game) {
        return entityManager.createNamedQuery("Comment.getAllComments", Comment.class)
                .setParameter("game", game).getResultList();
    }
}
