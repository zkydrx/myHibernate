package com.tyd.controller;

import com.tyd.Utils.SessionUtil;
import com.tyd.pojo.HibernateEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abbot
 * Date: 2017-09-02
 * Time: 16:21
 * Description:
 */
public class Domain
{
    public static void main(String[] args)
    {
        /**
         * 实例化configuration
         */
        Configuration configuration = new Configuration().configure();

        /**
         * 以configuration来创建SessionFactory实例
         */
        SessionFactory sf = configuration.buildSessionFactory();

        /**
         * 创建Session
         */
        Session session = sf.openSession();

        /**
         * 开启事务
         */
        Transaction transaction = session.beginTransaction();

        /**
         * 创建实体对象
         */
        HibernateEntity hibernateEntity = new HibernateEntity();
        hibernateEntity.setName("a");
        hibernateEntity.setAge("25");
        /**
         * 通过session保存实体
         */
        session.save(hibernateEntity);

        /**
         * 提交事务
         */
        transaction.commit();

        /**
         * 关闭Session
         */
        session.close();

        /**
         * 关闭SessionFactory
         */
        sf.close();
    }

    /**
     * 将一个List对象保存到数据库
     * @param list
     */
    public  void addToTable(List<HibernateEntity> list)
    {
        /**
         * 通过工具获取Session
         */
        Session session = SessionUtil.getSession();

        /**
         * 开启事务
         */
        Transaction transaction = session.beginTransaction();
//        hibernateEntity.forEach(hibernateEntity1->session.save(hibernateEntity1));
        for(int i = 0; i < list.size(); i++)
        {
            session.save(list.get(i));
        }
        transaction.commit();
        //        session.close();
    }

//    @Test
//    public void testAddToTable()
//    {
//        HibernateEntity hibernateEntity = new HibernateEntity();
//        hibernateEntity.setName("a");
//        hibernateEntity.setAge("25");
//        HibernateEntity hibernateEntity1 = new HibernateEntity();
//        hibernateEntity1.setName("b");
//        hibernateEntity1.setAge("25");
//        HibernateEntity hibernateEntity2 = new HibernateEntity();
//        hibernateEntity2.setName("3");
//        hibernateEntity2.setAge("25");
//        List<HibernateEntity> list = new ArrayList<>();
//        list.add(hibernateEntity);
//        list.add(hibernateEntity1);
//        list.add(hibernateEntity2);
////        addToTable(list);
//        Session session = SessionUtil.getSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(hibernateEntity);
//        transaction.commit();
//        session.close();
//
//    }
}
