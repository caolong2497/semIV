/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Common.Constant;
import Common.Utils;
import entity.Couple;
import entity.UserInfo;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public Boolean disconnectPartner(int coupleID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query;
        int i = 0;
        List<UserInfo> list = null;
        try {
            session.beginTransaction();
            query = session.createQuery("from UserInfo where coupleID=:coupleid");
            query.setParameter("coupleid", coupleID);
            list = query.list();
            if (list.size() == 2) {
                //update coupleid=6(mã coupleid mặc định) 
                String str = "update UserInfo u set u.coupleID = " + Constant.DEFEAULT_COUPLEID + " where u.coupleID = :coupleID";
                query = session.createQuery(str);
                query.setParameter("coupleID", coupleID);
                i = query.executeUpdate();

                if (i > 0) {
                    query = session.createQuery("delete from Couple where coupleID= :coupleId");
                    query.setParameter("coupleId", coupleID);
                    i = query.executeUpdate();
                    if (i > 0) {
                        query = session.createQuery("delete from Comment where userId in (:userId, :partnerId)");
                        query.setParameter("userId", list.get(0).getUserID());
                        query.setParameter("partnerId", list.get(1).getUserID());
                        query.executeUpdate();
                        query = session.createQuery("delete from Memory where userId in (:userId, :partnerId)");
                        query.setParameter("userId", list.get(0).getUserID());
                        query.setParameter("partnerId", list.get(1).getUserID());
                        query.executeUpdate();
                        session.getTransaction().commit();
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        session.getTransaction().rollback();
        return false;
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
        } finally {
            session.close();
        }
        return null;
    }

    public UserInfo getUserInfoByIDAndPassword(int userid, String password) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        UserInfo p = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where userID= :userID and password = :password");
            query.setParameter("userID", userid);
            query.setParameter("password", password);
            p = (UserInfo) query.uniqueResult();
            session.getTransaction().commit();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
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
            return null;
        } finally {
            session.close();
        }
    }

    public Boolean updateUserCustom(UserInfo userInfo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("update UserInfo set name=:username , birthday=:birthday,gender=:gender,avatar=:avatar where userId =:userId");
            query.setParameter("username", userInfo.getName());
            query.setParameter("birthday", userInfo.getBirthday());
            query.setParameter("gender", userInfo.isGender());
            query.setParameter("avatar", userInfo.getAvatar());
            query.setParameter("userId", userInfo.getUserID());
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
        } finally {
            session.close();
        }
        return false;
    }

    public UserInfo getUserInforByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserInfo p = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where gmail=:gmail");
            query.setParameter("gmail", email);
            query.setMaxResults(1).uniqueResult();
            p = (UserInfo) query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return p;
    }

    public UserInfo checkCode(String email, String code) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserInfo p = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where gmail=:gmail and code = :code");
            query.setParameter("gmail", email);
            query.setParameter("code", code);
            query.setMaxResults(1).uniqueResult();
            p = (UserInfo) query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return p;
    }

    public int getUserIdByCode(int userid, String code) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from UserInfo where code=:code and userID != :userid and coupleID=" + Constant.DEFEAULT_COUPLEID);
            query.setParameter("code", code);
            query.setParameter("userid", userid);
            query.setMaxResults(1).uniqueResult();
            UserInfo p = (UserInfo) query.uniqueResult();
            if (p != null) {
                session.getTransaction().commit();
                return p.getUserID();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return 0;
    }

    /**
     *
     * @param userid mã user người login
     * @param idPartner mã partner
     * @return coupleid nếu thành công,6 nếu thất bại
     */
    public int createCouple(int userid, int idPartner) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tranc = null;
        Couple couple = new Couple(0, Utils.StringToSQLDate("01/01/1900"), "default");
        Query query;
        try {
            tranc = session.beginTransaction();
            session.save(couple);
            query = session.createSQLQuery("select max(coupleid) from tbl_couple");
            int LastcoupleId = (Integer) query.setMaxResults(1).uniqueResult();
            query = session.createQuery("update UserInfo set coupleID=:coupleId where userID in(:userid, :partnerId) and coupleID = " + Constant.DEFEAULT_COUPLEID);
            query.setParameter("coupleId", LastcoupleId);
            query.setParameter("userid", userid);
            query.setParameter("partnerId", idPartner);
            int i = query.executeUpdate();
            if (i == 2) {
                tranc.commit();
                return LastcoupleId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        tranc.rollback();
        return Constant.DEFEAULT_COUPLEID;
    }

}
