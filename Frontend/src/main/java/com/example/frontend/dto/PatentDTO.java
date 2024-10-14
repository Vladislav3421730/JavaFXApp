package com.example.frontend.dto;

import javafx.scene.control.Button;

import java.math.BigDecimal;
import java.util.Date;

public class PatentDTO {
    private Integer id;
    private String name;
    private BigDecimal cost;
    private String company;
    private Float validity_period;
    private Date purchase_date;
    private Button delete;
    private Button change;

    public PatentDTO(Integer id, String name, BigDecimal cost, String company, float validity_period, Date purchase_date, Button delete, Button change) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.company = company;
        this.validity_period = validity_period;
        this.purchase_date = purchase_date;
        this.delete = delete;
        this.change = change;
    }

    public PatentDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public float getValidity_period() {
        return validity_period;
    }

    public void setValidity_period(float validity_period) {
        this.validity_period = validity_period;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public Button getChange() {
        return change;
    }

    public void setChange(Button change) {
        this.change = change;
    }
}
