package org.example.services;

import org.example.dao.UserDAO;
import org.example.models.User;
import org.example.models.enums.Role;

import java.util.List;
import java.util.stream.Collectors;


public class UserService implements Service<User> {

    private final UserDAO userDAO=UserDAO.getInstance();

    private  static final UserService INSTANCE=new UserService();
    private UserService(){}
    public static UserService getInstance(){
        return INSTANCE;
    }

    @Override
    public void Save(User user) {
        userDAO.save(user);
    }

    @Override
    public void DeleteById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public void Update(User user) {
        userDAO.update(user);
    }

    @Override
    public User FindById(int id) {
       return userDAO.findById(id).orElse(null);
    }

    @Override
    public List<User> FindAll() {
        return userDAO.findAll().stream()
                .filter(user -> !user.getRoles().contains(Role.ADMIN))
                .collect(Collectors.toList());
    }


    public User FindByLogin(String login){
        return userDAO.findByLogin(login).orElse(null);
    }
}
