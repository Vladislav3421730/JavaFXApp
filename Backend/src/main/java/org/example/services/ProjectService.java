package org.example.services;

import org.example.dao.ProjectDAO;
import org.example.dao.UserDAO;
import org.example.models.Project;

import java.util.List;

public class ProjectService implements Service<Project> {

   private final ProjectDAO projectDAO=ProjectDAO.getInstance();

    private  static final ProjectService INSTANCE=new ProjectService();
    private ProjectService(){}
    public static ProjectService getInstance(){
        return INSTANCE;
    }

    @Override
    public void Save(Project project) {
        projectDAO.save(project);
    }

    @Override
    public void DeleteById(int id) {

    }

    @Override
    public void Update(Project object) {

    }

    @Override
    public Project FindById(int id) {
        return null;
    }

    @Override
    public List<Project> FindAll() {
        return projectDAO.findAll();

    }
}
