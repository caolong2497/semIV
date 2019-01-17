/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Couple;
import java.sql.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author chinam
 */
public class CoupleDAO {

    public List<Couple> getCouples() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List list = session.createQuery("from Couple").list();
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

    public Boolean addCouple(Couple couple) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(couple);
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

    public Boolean updateCouple(Couple couple) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(couple);
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

    public Boolean deleteCouple(int coupleId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("delete from Couple where coupleID= :coupleId");
            query.setParameter("coupleId", coupleId);
            int i = query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            session.close();
        }
        return false;
    }

    public Couple getCoupleById(int coupleId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Couple p = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Couple where coupleID= :coupleId");
            query.setParameter("coupleId", coupleId);
            p = (Couple) query.uniqueResult();
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

    public Boolean updateStartDate(int coupleID, Date startdate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            String str = "update Couple c set c.start=:startdate where c.coupleID =:coupleID";
            Query query = session.createQuery(str);
            query.setParameter("coupleID", coupleID);
            query.setParameter("startdate", startdate);
            int i = query.executeUpdate();
            if (i > 0) {
                session.getTransaction().commit();
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

    public Boolean updateCoupleImage(Couple couple) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            String str = "update Couple c set c.image=:imagelink where c.coupleID =:coupleID";
            Query query = session.createQuery(str);
            query.setParameter("coupleID", couple.getCoupleID());
            query.setParameter("imagelink", couple.getImage());
            int i = query.executeUpdate();
            session.getTransaction().commit();
            if (i > 0) {
                return true;
            }

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

//    public static void main(String[] args) {
//        String date = "05-01-2019";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        java.util.Date startdate;
//        try {
//            startdate = simpleDateFormat.parse(date);
//            java.sql.Date sqlDate = new java.sql.Date(startdate.getTime());
//            System.out.println("value:"+new CoupleDAO().updateStartDate(1, sqlDate));
//        } catch (ParseException ex) {
//            Logger.getLogger(CoupleDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
