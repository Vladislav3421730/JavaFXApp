package com.example.frontend.models.entities;

import com.example.frontend.models.enums.Role;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {

    private int id;
    private String login;
    private String password;
    private Set<Role> roles;
    private boolean isBun;
    private PersonData personData;

    private User(Builder builder) {
        this.id = builder.id;
        this.login = builder.login;
        this.password = builder.password;
        this.roles = builder.roles;
        this.isBun = builder.isBun;
        this.personData = builder.personData;
    }
    public  User(){}



    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setBun(boolean bun) {
        isBun = bun;
    }

    public void setPersonData(PersonData personData) {
        this.personData = personData;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isBun() {
        return isBun;
    }

    public PersonData getPersonData() {
        return personData;
    }

    public static class Builder {
        private int id;
        private String login;
        private String password;
        private Set<Role> roles = new HashSet<>();
        private boolean isBun;
        private PersonData personData;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRoles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public Builder setBun(boolean isBun) {
            this.isBun = isBun;
            return this;
        }

        public Builder setPersonData(PersonData personData) {
            this.personData = personData;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
