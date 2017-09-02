package com.tyd.controller;

import com.tyd.pojo.HibernateEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abbot
 * Date: 2017-09-02
 * Time: 17:46
 * Description:
 */
public class TestDoMain
{

    public static void main(String[] args)
    {
//        HibernateEntity hibernateEntity = new HibernateEntity();
//        hibernateEntity.setName("a");
//        hibernateEntity.setAge("25");
//        Session session = SessionUtil.getSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(hibernateEntity);
//        transaction.commit();
//        session.close();

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
        Domain domain = new Domain();
        domain.addToTable(list);
    }
}
