package com.example.frontend.mappers;

import com.example.frontend.dto.UserDTO;
import com.example.frontend.models.entities.User;
import com.example.frontend.models.enums.Role;
import javafx.scene.control.Button;

import java.util.stream.Collectors;

public class MapToUserDTO {
    public static UserDTO mapToUserDto(User user){
        UserDTO userDTO=new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setId(user.getId());
        userDTO.setSalary(user.getPersonData().getSalary());
        userDTO.setName(user.getPersonData().getName());
        userDTO.setSurname(user.getPersonData().getSurname());
        userDTO.setRoles(user.getRoles().stream().map(Role::name).collect(Collectors.joining(" ")));

        Button button =new Button();
        button.setText(user.isBun() ? "Разбанить" : "Забанить");
        button.setId(String.valueOf(user.getId()));
        userDTO.setIsBun(button);

        Button buttonAddRole =new Button();
        buttonAddRole.setText(user.getRoles().contains(Role.MANAGER) ? "Лишить" : "Сделать");
        buttonAddRole.setId(String.valueOf(user.getId()));
        userDTO.setAddRole(buttonAddRole);

        Button buttonDelete =new Button("Удалить");
        buttonDelete.setId(String.valueOf(user.getId()));
        userDTO.setDelete(buttonDelete);
        return  userDTO;
    }
}
