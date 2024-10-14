package org.example.services;

import org.example.dao.EquipmentDAO;
import org.example.models.Equipment;

import java.util.List;

public class EquipmentService implements Service<Equipment> {

    private final EquipmentDAO equipmentDAO=EquipmentDAO.getInstance();

    private  static final EquipmentService INSTANCE=new EquipmentService();
    private EquipmentService(){}
    public static EquipmentService getInstance(){
        return INSTANCE;
    }

    @Override
    public void Save(Equipment equipment) {
        equipmentDAO.save(equipment);
    }

    @Override
    public void DeleteById(int id) {

    }

    @Override
    public void Update(Equipment object) {

    }

    @Override
    public Equipment FindById(int id) {
        return null;
    }

    @Override
    public List<Equipment> FindAll() {
        return equipmentDAO.findAll();
    }
}
