package com.tyd.Utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: Abbot
 * Date: 2017-09-02
 * Time: 17:01
 * Description:
 */
public class SessionUtil
{
    public static Session getSession()
    {
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        return session;
    }

    public static void main(String[] args)
    {
        Session session = getSession();
        System.out.println(session==null?"fail connect":"connect success");
    }
}
