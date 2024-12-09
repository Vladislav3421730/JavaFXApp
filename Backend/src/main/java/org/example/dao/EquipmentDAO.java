package org.example.dao;

import org.example.models.Equipment;
import org.example.models.Patent;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class EquipmentDAO implements DAO<Equipment> {

    private static SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();

    private static final EquipmentDAO INSTANCE=new EquipmentDAO();
    private EquipmentDAO(){};
    public static EquipmentDAO getInstance(){
        return INSTANCE;
    }

    @Override
    public void save(Equipment equipment) {
         Session session= sessionFactory.openSession();
         session.beginTransaction();
         session.save(equipment);
         session.getTransaction().commit();
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Equipment equipment = session.get(Equipment.class, id);
        if (equipment != null) {
            Equipment equipmentMerge = (Equipment) session.merge(equipment);
            session.delete(equipmentMerge);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Equipment equipment) {
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(equipment);
        session.getTransaction().commit();
    }

    @Override
    public Optional<Equipment> findById(int id) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        Equipment equipment=session.get(Equipment.class,id);
        session.getTransaction().commit();
        return Optional.ofNullable(equipment);
    }

    @Override
    public List<Equipment> findAll() {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        List<Equipment> equipmentList=session.createQuery("from equipment e",Equipment.class).getResultList();
        session.getTransaction().commit();
        return equipmentList;
    }
}
