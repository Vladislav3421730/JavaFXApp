package org.example.dao;

import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;


public class UserDAO implements DAO<User>  {
    private static SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();

    private static final UserDAO INSTANCE=new UserDAO();
    private UserDAO(){};
    public static UserDAO getInstance(){
        return INSTANCE;
    }

    @Override
    public void save(User user) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        if (user != null) {
            User mergedUser = (User) session.merge(user);
            session.delete(mergedUser);
        }
        session.getTransaction().commit();
        session.close();
    }


    @Override
    public void update(User user) {
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
    }

    @Override
    public Optional<User> findById(int id) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        User user=session.get(User.class,id);
        session.getTransaction().commit();
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        List<User> users=session.createQuery("from User u",User.class).getResultList();
        session.getTransaction().commit();
        return users;

    }



    public Optional<User> findByLogin(String login){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        User user=session.createQuery("FROM User u WHERE u.login = :login", User.class)
                        .setParameter("login",login)
                        .uniqueResult();
        session.getTransaction().commit();
        return Optional.ofNullable(user);
    }
}
