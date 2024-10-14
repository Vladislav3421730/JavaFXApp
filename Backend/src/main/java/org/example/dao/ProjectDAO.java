package org.example.dao;

import org.example.models.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class ProjectDAO implements DAO<Project> {

    private static SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();

    private static final ProjectDAO INSTANCE=new ProjectDAO();
    private ProjectDAO(){};
    public static ProjectDAO getInstance(){
        return INSTANCE;
    }

    @Override
    public void save(Project project) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.save(project);
        session.getTransaction().commit();
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(Project object) {

    }

    @Override
    public Optional<Project> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Project> findAll() {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        List<Project> projects=session.createQuery("from Project p", Project.class).getResultList();
        session.getTransaction().commit();
        return projects;
    }
}
