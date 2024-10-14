package com.example.frontend.dto;

import javafx.scene.control.Button;

import java.math.BigDecimal;


public class UserDTO {
    private Integer id;
    private String login;
    private String roles;
    private Button isBun;
    private Button AddRole;
    private Button Delete;
    private String name;
    private String surname;
    private BigDecimal salary;

    public UserDTO(String login, String name, String surname, BigDecimal salary) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    public UserDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Button getIsBun() {
        return isBun;
    }

    public void setIsBun(Button isBun) {
        this.isBun = isBun;
    }

    public Button getAddRole() {
        return AddRole;
    }

    public void setAddRole(Button addRole) {
        AddRole = addRole;
    }

    public Button getDelete() {
        return Delete;
    }

    public void setDelete(Button delete) {
        Delete = delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
