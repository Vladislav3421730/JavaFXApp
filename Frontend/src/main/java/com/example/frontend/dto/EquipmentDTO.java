package com.example.frontend.dto;

import javafx.scene.control.Button;

import java.math.BigDecimal;
import java.util.Date;

public class EquipmentDTO {
    private int id;
    private String name;
    private BigDecimal cost;
    private Date purchase_date;
    private String description;
    private Button change;
    private Button delete;

    public EquipmentDTO() {}

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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Button getChange() {
        return change;
    }

    public void setChange(Button change) {
        this.change = change;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
