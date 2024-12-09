package org.example.dao;

import org.example.models.Project;
import org.example.models.User;
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Project project = session.get(Project.class, id);
        if (project != null) {
            Project mergedProject = (Project) session.merge(project);
            session.delete(mergedProject);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Project project) {
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(project);
        session.getTransaction().commit();
    }

    @Override
    public Optional<Project> findById(int id) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        Project project=session.get(Project.class,id);
        session.getTransaction().commit();
        return Optional.ofNullable(project);
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
