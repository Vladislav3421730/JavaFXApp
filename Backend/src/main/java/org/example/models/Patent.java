package org.example.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="patents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String name;
    private BigDecimal cost;
    private String company;
    private float validity_period;
    private Date purchase_date;


}
