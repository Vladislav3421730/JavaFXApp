package com.example.frontend.models.entities;


import java.math.BigDecimal;
import java.util.Date;

public class Patent {
    private int id;
    private String name;
    private BigDecimal cost;
    private String company;
    private float validity_period;
    private Date purchase_date;

    public Patent(int id, String name, BigDecimal cost, String company, float validity_period, Date purchase_date) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.company = company;
        this.validity_period = validity_period;
        this.purchase_date = purchase_date;
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

    public Patent() {}

    public static PatentBuilder builder() {
        return new PatentBuilder();
    }

    public static class PatentBuilder {
        private int id;
        private String name;
        private BigDecimal cost;
        private String company;
        private float validity_period;
        private Date purchase_date;

        public PatentBuilder() {
        }

        public PatentBuilder id(int id) {
            this.id = id;
            return this;
        }

        public PatentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PatentBuilder cost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public PatentBuilder company(String company) {
            this.company = company;
            return this;
        }

        public PatentBuilder validity_period(float validity_period) {
            this.validity_period = validity_period;
            return this;
        }

        public PatentBuilder purchase_date(Date purchase_date) {
            this.purchase_date = purchase_date;
            return this;
        }

        public Patent build() {
            return new Patent(id, name, cost, company, validity_period, purchase_date);
        }

    }
}
