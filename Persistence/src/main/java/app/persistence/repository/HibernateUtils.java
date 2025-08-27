package app.persistence.repository;

import app.model.Configuration;
import app.model.Game;
import app.model.Player;
import app.model.Position;
import org.hibernate.SessionFactory;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory == null) || (sessionFactory.isClosed()))
            sessionFactory = createNewSessionFactory();
        return sessionFactory;
    }

    private static SessionFactory createNewSessionFactory() {
        sessionFactory = new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(Player.class)
                .addAnnotatedClass(Game.class)
                .addAnnotatedClass(Position.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        if (sessionFactory != null)
            sessionFactory.close();
    }
}
