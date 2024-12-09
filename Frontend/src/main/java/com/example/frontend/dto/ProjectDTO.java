package com.example.frontend.dto;

import com.example.frontend.models.entities.Equipment;
import com.example.frontend.models.entities.Patent;
import com.example.frontend.models.entities.PersonData;
import javafx.scene.control.Button;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectDTO {
    private int id;
    private String name;
    private String description;
    private Date start_date;
    private Date end_date;
    private String status;
    private BigDecimal budget;
    private Button info;
    private List<Equipment> equipmentList=new ArrayList<>();
    private List<PersonData> employees=new ArrayList<>();
    private List<Patent> patents=new ArrayList<>();

    public ProjectDTO() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Button getInfo() {
        return info;
    }

    public void setInfo(Button info) {
        this.info = info;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<PersonData> getEmployees() {
        return employees;
    }

    public void setEmployees(List<PersonData> employees) {
        this.employees = employees;
    }

    public List<Patent> getPatents() {
        return patents;
    }

    public void setPatents(List<Patent> patents) {
        this.patents = patents;
    }
}
