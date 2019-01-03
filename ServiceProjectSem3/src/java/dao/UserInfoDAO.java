/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.UserInfo;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author chinam
 */
public class UserInfoDAO {

    public List<UserInfo> getUserInfos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List list = session.createQuery("from UserInfo").list();
            session.getTransaction().commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }

    public Boolean addUserInfo(UserInfo userInfo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(userInfo);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }

    }

    public Boolean updateUserInfo(UserInfo userInfo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(userInfo);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    public Boolean deleteUserInfo(int userID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("delete from UserInfo where userID= :UserId");
            query.setParameter("UserId", userID);
            int i = query.executeUpdate();
            session.getTransaction().commit();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();

        } finally {
            session.close();
        }
        return false;
    }

    public UserInfo getUserInfoById(int userID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserInfo p = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where userID= :userID");
            query.setParameter("userID", userID);
            p = (UserInfo) query.uniqueResult();
            session.getTransaction().commit();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }

    public UserInfo getUserInfoByGmailAndPassword(String gmail, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserInfo p = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where gmail= :gmail and password = :password");
            query.setParameter("gmail", gmail);
            query.setParameter("password", password);
            p = (UserInfo) query.uniqueResult();
            session.getTransaction().commit();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }
    public UserInfo getUserInfoByIDAndPassword(String userid, String password) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserInfo p = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where userID= :userID and password = :password");
            query.setParameter("userID", Integer.parseInt(userid));
            query.setParameter("password", password);
            p = (UserInfo) query.uniqueResult();
            session.getTransaction().commit();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }
    public List<UserInfo> getUserInfosByCoupleID(int coupleid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserInfo> list = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where coupleID=:coupleid");
            query.setParameter("coupleid", coupleid);
            list = query.list();
            session.getTransaction().commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

}
