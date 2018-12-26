/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Comment;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author chinam
 */
public class CommentDAO {
    public List<Comment> getComments(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List list = session.createQuery("from Comment").list();
            session.getTransaction().commit();
            session.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
        }
        return null;
    }
    
    public Boolean addComment(Comment comment){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(comment);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
        }
        return false;
    }
    
    public Boolean updateComment(Comment comment){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(comment);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
        }
        return false;
    }
    
    public Boolean deleteComment(int commentID){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("delete from Comment where commentID= :commentID");
            query.setParameter("commentID", commentID);
            int i = query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            if(i>0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
        }
        return false;
    }
    
    public Comment getCommentById(int commentID){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Comment p = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Comment where commentID= :commentID");
            query.setParameter("commentID", commentID);
            p = (Comment) query.uniqueResult();
            session.getTransaction().commit();
            session.close();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
        }
        return null;
    }
}
