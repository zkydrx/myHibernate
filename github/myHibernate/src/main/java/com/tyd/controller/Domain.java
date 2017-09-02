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
        for(int i = 0; i <= 1000; i++)
        {
            session.save(list.get(0));

            /**
             * 这两句至关重要，第一句session.flush()刷新缓存，第二句
             * session.clear()是清空session为下一次进行操作准备，如果没有这句将会报异常
             *
             * Exception in thread "main" org.hibernate.NonUniqueObjectException: A different object with the same identifier value was already associated with the session : [com.tyd.pojo.HibernateEntity#0]
             at org.hibernate.event.internal.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:169)
             at org.hibernate.event.internal.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:125)
             at org.hibernate.event.internal.DefaultSaveOrUpdateEventListener.saveWithGeneratedOrRequestedId(DefaultSaveOrUpdateEventListener.java:192)
             at org.hibernate.event.internal.DefaultSaveEventListener.saveWithGeneratedOrRequestedId(DefaultSaveEventListener.java:38)
             at org.hibernate.event.internal.DefaultSaveOrUpdateEventListener.entityIsTransient(DefaultSaveOrUpdateEventListener.java:177)
             at org.hibernate.event.internal.DefaultSaveEventListener.performSaveOrUpdate(DefaultSaveEventListener.java:32)
             at org.hibernate.event.internal.DefaultSaveOrUpdateEventListener.onSaveOrUpdate(DefaultSaveOrUpdateEventListener.java:73)
             at org.hibernate.internal.SessionImpl.fireSave(SessionImpl.java:689)
             at org.hibernate.internal.SessionImpl.save(SessionImpl.java:681)
             at org.hibernate.internal.SessionImpl.save(SessionImpl.java:676)

             意思是说，一个不同的对象具有相同的标识符的已经存在于session中，所以没办法对新的元素进行操作这里是保存操作。
             所以这时我们就要对session的缓存进行更新，并且清空session以用来对新的元素进行绑定操作。
             */
            session.flush();
            session.clear();


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
