package com.example.frontend.models.entities;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Project {
    private int id;
    private String name;
    private String description;
    private Date start_date;
    private Date end_date;
    private String status;
    private BigDecimal budget;
    private List<Equipment> equipmentList=new ArrayList<>();
    private List<PersonData> employees=new ArrayList<>();
    private List<Patent> patents=new ArrayList<>();

    public Project(int id, String name, String description, Date start_date, Date end_date, String status, BigDecimal budget) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.budget = budget;
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

    public Project() {}

    public static ProjectBuilder builder() {
        return new ProjectBuilder();
    }

    public void AddNewEmployeeToProject(PersonData employee){
        employees.add(employee);
    }

    public void AddNewPatentToProject(Patent patent){
        patents.add(patent);
    }

    public void AddNewEquipmentToProject(Equipment equipment){
        equipmentList.add(equipment);
    }


    public static class ProjectBuilder {
        private int id;
        private String name;
        private String description;
        private Date start_date;
        private Date end_date;
        private String status;
        private BigDecimal budget;

        public ProjectBuilder() {
        }

        public ProjectBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public ProjectBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProjectBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ProjectBuilder setStartDate(Date start_date) {
            this.start_date = start_date;
            return this;
        }

        public ProjectBuilder setEndDate(Date end_date) {
            this.end_date = end_date;
            return this;
        }

        public ProjectBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public ProjectBuilder setBudget(BigDecimal budget) {
            this.budget = budget;
            return this;
        }

        public Project build() {
            return new Project(id, name, description, start_date, end_date, status, budget);
        }

    }

}
