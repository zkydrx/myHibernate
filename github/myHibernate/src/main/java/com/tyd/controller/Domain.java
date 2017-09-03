package com.tyd.controller;

import com.tyd.Utils.FactoryUtil;
import com.tyd.Utils.MD5Util;
import com.tyd.Utils.SessionUtil;
import com.tyd.pojo.HibernateEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
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
        session.close();
    }




    @Test
    public void testAddToTable()
    {
        HibernateEntity hibernateEntity = new HibernateEntity();
        hibernateEntity.setName("a");
        hibernateEntity.setAge("25");
        HibernateEntity hibernateEntity1 = new HibernateEntity();
        hibernateEntity1.setName("b");
        hibernateEntity1.setAge("25");
        HibernateEntity hibernateEntity2 = new HibernateEntity();
        hibernateEntity2.setName("3");
        hibernateEntity2.setAge("25");
        List<HibernateEntity> list = new ArrayList<>();
        list.add(hibernateEntity);
        list.add(hibernateEntity1);
        list.add(hibernateEntity2);
        addToTable(list);

    }


    /**
     * 根据id删除一条数据
     * @param id
     */
    public static void deleteObject(Integer id)
    {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        HibernateEntity hibernateEntity = new HibernateEntity();
        hibernateEntity.setId(id);
        session.delete(hibernateEntity);
        session.flush();
        session.clear();
        transaction.commit();
        session.close();
    }

    @Test
    public void testDeleteObject()
    {
        deleteObject(1);
    }

    public static void updateObject(HibernateEntity hibernateEntity)
    {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(hibernateEntity);
        session.flush();
        session.clear();
        transaction.commit();
        session.close();
    }

    @Test
    public void testUpdateObject()
    {
        HibernateEntity hibernateEntity = new HibernateEntity();
        hibernateEntity.setId(2);
        hibernateEntity.setAge("10000");
        hibernateEntity.setName("zky");
        updateObject(hibernateEntity);
    }

    /**
     * 按名字查找用户信息
     * @param name
     * @return
     */
    public static List<HibernateEntity> findObjectByName(String name)
    {
        HibernateEntity hibernateEntity = new HibernateEntity();
        hibernateEntity.setName(name);
        SessionFactory sessionFactory = FactoryUtil.getSessionFactory();
        EntityManager entityManager = sessionFactory.createEntityManager();
        /**
         * entity管理开启事务
         */
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("from HibernateEntity h where h.name=:name");
        query.setParameter("name","zky");
        List resultList = query.getResultList();
        entityManager.getTransaction().commit();
        sessionFactory.close();
        System.out.println(resultList);
        return resultList;
    }

    @Test
    public void testFindObjectByName()
    {
        List<HibernateEntity> zky = findObjectByName("zky");
        System.out.println(zky);
    }


    /**
     * 查找所有的用户信息
     * @return
     */
    public static List<HibernateEntity> getAllEntity()
    {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        List list = session.createQuery("from HibernateEntity ").list();
        transaction.commit();
        session.close();
        return list;
    }

    @Test
    public void testGetAllEntity()
    {
        List<HibernateEntity> allEntity = getAllEntity();
        System.out.println(allEntity);
        System.out.println(allEntity.size());
    }


    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    public static HibernateEntity findById(Integer id)
    {
        SessionFactory sessionFactory = FactoryUtil.getSessionFactory();
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String hql ="from HibernateEntity h where h.id=:id" ;
        Query query = entityManager.createQuery(hql);
        query.setParameter("id",id);
        List<HibernateEntity> resultList = query.getResultList();
        entityManager.getTransaction().commit();
        sessionFactory.close();
        return resultList.get(0);
    }

    @Test
    public void testFinById()
    {
        HibernateEntity byId = findById(3);
        System.out.println(byId);

    }


    /**
     * 根据年龄查询用户信息
     * @param age
     * @return
     */
    public List<HibernateEntity> findByAge(Integer age)
    {
        SessionFactory sessionFactory = FactoryUtil.getSessionFactory();
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(" from HibernateEntity h where h.age > :_age");
        query.setParameter("_age",String.valueOf(age));
        List resultList = query.getResultList();
        entityManager.getTransaction().commit();
        sessionFactory.close();
        return resultList;
    }

    @Test
    public void testFindByAge()
    {
        Domain domain = new Domain();
        List<HibernateEntity> byAge = domain.findByAge(18);
        System.out.println("They are adult:"+byAge);

    }

    /**
     * 用户名模糊查询
     * @param name
     * @return
     */
    public List<HibernateEntity> findByNameLikeAs(String name)
    {
        SessionFactory sessionFactory = FactoryUtil.getSessionFactory();
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from HibernateEntity h where h.name like :_name order by h.id desc");
        query.setParameter("_name","%"+name+"%");
        List resultList = query.getResultList();
        entityManager.getTransaction().commit();
        sessionFactory.close();
        return resultList;
    }

    @Test
    public void testFindByNameLikeAs()
    {
        Domain domain =  new Domain();
        List<HibernateEntity> z = domain.findByNameLikeAs("j");
        System.out.println("模糊查询结果："+z);
    }

    /**
     * 用户登陆校验，姓名和密码进行数据库校验，如果跟数据库的用户名和密码一致则返回真，否者返回假。
     * @param name
     * @param password
     * @return
     */
    public boolean loginCheck(String name,String password)
    {
        SessionFactory sessionFactory = FactoryUtil.getSessionFactory();
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("from HibernateEntity h where h.name=:_name and h.password=:_password");
        query.setParameter("_name",name);
        String md5 = MD5Util.getMD5(password);
        query.setParameter("_password",md5);
        List resultList = query.getResultList();
        entityManager.getTransaction().commit();
        sessionFactory.close();
        if(resultList.size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Test
    public void testLoginCheck()
    {
        Domain domain = new Domain();
        boolean zky1 = domain.loginCheck("zky", "zky");
        if(zky1)
        {
            System.out.println("检测到数据库有该条数据，登陆成功，跳转到首页。");
        }
        else
        {
            System.out.println("用户名或密码错误，请重试，跳转到登录页");
        }
    }

}
