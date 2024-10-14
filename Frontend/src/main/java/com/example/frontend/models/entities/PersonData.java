package com.example.frontend.models.entities;

import com.example.frontend.models.enums.Gender;


import java.io.Serializable;
import java.math.BigDecimal;

public class PersonData implements Serializable {

    private int id;
    private String name;
    private String surname;
    private BigDecimal salary;
    private String email;
    private int age;
    private Gender gender;
    private String position;


    private PersonData(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.surname = builder.surname;
        this.salary = builder.salary;
        this.email = builder.email;
        this.age = builder.age;
        this.gender = builder.gender;
        this.position=builder.position;
    }


    public PersonData() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public static class Builder {
        private int id;
        private String name;
        private String surname;
        private BigDecimal salary;
        private String email;
        private int age;
        private Gender gender;
        private String position;


        public Builder setPosition(String position) {
            this.position = position;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setSalary(BigDecimal salary) {
            this.salary = salary;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public PersonData build() {
            return new PersonData(this);
        }


    }

}
