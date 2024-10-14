package com.example.frontend.models.entities;


import java.math.BigDecimal;
import java.util.Date;

public class Equipment {
    private int id;
    private String name;
    private BigDecimal cost;
    private Date purchase_date;
    private String description;

    public Equipment() {
    }

    public Equipment(int id, String name, BigDecimal cost, Date purchase_date,String description) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.purchase_date = purchase_date;
        this.description=description;
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

    public void SetDescription(String description){this.description=description;}

    public BigDecimal getCost() {
        return cost;
    }

    public String getDescription(){return description;}

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public static EquipmentBuilder builder() {
        return new EquipmentBuilder();
    }

    public static class EquipmentBuilder {
        private int id;
        private String name;
        private BigDecimal cost;
        private Date purchase_date;
        private String description;

        public EquipmentBuilder() {}

        public EquipmentBuilder id(int id) {
            this.id = id;
            return this;
        }

        public EquipmentBuilder description(String description){
            this.description=description;
            return this;
        }

        public EquipmentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EquipmentBuilder cost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public EquipmentBuilder purchase_date(Date purchase_date) {
            this.purchase_date = purchase_date;
            return this;
        }

        public Equipment build() {
            return new Equipment(id, name, cost, purchase_date,description);
        }

    }

}
