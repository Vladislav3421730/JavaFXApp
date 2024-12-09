package org.example.services;

import org.example.dao.PatentDAO;
import org.example.models.Patent;

import java.util.List;

public class PatentService implements Service<Patent> {

    private final PatentDAO patentDAO=PatentDAO.getInstance();

    private  static final PatentService INSTANCE=new PatentService();
    private PatentService(){}
    public static PatentService getInstance(){
        return INSTANCE;
    }

    @Override
    public void Save(Patent patent) {
        patentDAO.save(patent);
    }

    @Override
    public void DeleteById(int id) {
        patentDAO.deleteById(id);
    }

    @Override
    public void Update(Patent patent) {
        patentDAO.update(patent);
    }

    @Override
    public Patent FindById(int id) {
        return patentDAO.findById(id).orElse(null);
    }

    @Override
    public List<Patent> FindAll() {
       return patentDAO.findAll();
    }
}
