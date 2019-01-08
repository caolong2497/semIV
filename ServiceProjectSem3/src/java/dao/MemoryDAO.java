/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Common.Utils;
import entity.Memory;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Memory_Model;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

/**
 *
 * @author chinam
 */
public class MemoryDAO {

    public List<Memory> getMemorys() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List list = session.createQuery("from Memory").list();
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

    public Boolean addMemory(Memory memory) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(memory);
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

    public Boolean updateMemory(int memoryId, String caption, Date time) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            String sql = "update Memory m set m.caption=:caption , m.time=:time where m.memoryId =:memoryId";
            Query query = session.createQuery(sql);
            query.setParameter("memoryId", memoryId);
            query.setParameter("caption", caption);
            query.setParameter("time", time);
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

    public Boolean deleteMemory(int memoryId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query2 = session.createQuery("delete from Comment where memoryId= :memoryId");
            query2.setParameter("memoryId", memoryId);
            query2.executeUpdate();
            Query query = session.createQuery("delete from Memory where memoryId= :memoryId");
            query.setParameter("memoryId", memoryId);
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

    public Memory getMemoryById(int memoryId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Memory p = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Memory where memoryId= :memoryId");
            query.setParameter("memoryId", memoryId);
            p = (Memory) query.uniqueResult();
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

    public List<Memory_Model> getMemoryByCoupleId(int coupleid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Memory_Model> listMemorys = new ArrayList<Memory_Model>();
        try {
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery("select M.memoryId as memoryId,M.image as image,M.time as timepost,\n"
                    + "M.caption as caption,ISNULL(C.countComment,0) as totalComment,M.userId as userId\n"
                    + " from tbl_memory M left join\n"
                    + "(select memoryId,count(commentId) as countComment from tbl_comment  group by memoryId ) C\n"
                    + "on M.memoryId = C.memoryId where userId in (select U.userid from tbl_userInfor U where U.coupleID=:coupleid)\n"
                    + "order by timepost desc ,memoryId desc")
                    .addScalar("memoryId", new IntegerType())
                    .addScalar("image", new StringType())
                    .addScalar("timepost", new DateType())
                    .addScalar("caption", new StringType())
                    .addScalar("totalComment", new IntegerType())
                    .addScalar("userId", new IntegerType());
            query.setParameter("coupleid", coupleid);
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                Memory_Model memory = new Memory_Model();
                memory.setMemoryId(Integer.parseInt(row[0].toString()));
                memory.setImage(row[1].toString());
                String time = Utils.convertFormatStringDate(row[2].toString(), "yyyy-MM-dd", "dd/MM/yyyy");
                memory.setTime(time);
                memory.setCaption(row[3].toString());
                memory.setCountComment(Integer.parseInt(row[4].toString()));
                memory.setUserId(Integer.parseInt(row[5].toString()));
                listMemorys.add(memory);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return listMemorys;
    }
//
//    public static void main(String[] args) {
//            System.out.println(new MemoryDAO().getMemoryByCoupleId(1));
//    }
}
