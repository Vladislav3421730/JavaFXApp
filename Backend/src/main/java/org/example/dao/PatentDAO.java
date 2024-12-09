package org.example.dao;

import org.example.models.Patent;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class PatentDAO implements DAO<Patent> {

    private static SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();

    private static final PatentDAO INSTANCE=new PatentDAO();
    private PatentDAO(){};
    public static PatentDAO getInstance(){
        return INSTANCE;
    }

    @Override
    public void save(Patent patent) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.save(patent);
        session.getTransaction().commit();
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Patent patent = session.get(Patent.class, id);
        if (patent != null) {
            Patent mergedPatent = (Patent) session.merge(patent);
            session.delete(mergedPatent);
        }
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void update(Patent patent) {
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(patent);
        session.getTransaction().commit();
    }

    @Override
    public Optional<Patent> findById(int id) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        Patent patent=session.get(Patent.class,id);
        session.getTransaction().commit();
        return Optional.ofNullable(patent);
    }

    @Override
    public List<Patent> findAll() {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        List<Patent> patents=session.createQuery("from Patent p",Patent.class).getResultList();
        session.getTransaction().commit();
        return patents;
    }
}
