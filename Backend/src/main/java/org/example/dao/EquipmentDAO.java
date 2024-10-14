package org.example.dao;

import org.example.models.Equipment;
import org.example.models.Patent;
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

    }

    @Override
    public void update(Equipment object) {

    }

    @Override
    public Optional<Equipment> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Equipment> findAll() {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        List<Equipment> equipmentList=session.createQuery("from Equipment e",Equipment.class).getResultList();
        session.getTransaction().commit();
        return equipmentList;
    }
}
