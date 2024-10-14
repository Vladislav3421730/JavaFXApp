package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private Date start_date;
    private Date end_date;
    private String status;
    private BigDecimal budget;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name="equipment_project",
            inverseJoinColumns  = @JoinColumn(name="equipment_id"),
            joinColumns=@JoinColumn(name="project_id")
    )
    private List<Equipment> equipmentList;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name="person_data_project",
            inverseJoinColumns  = @JoinColumn(name="person_data_id"),
            joinColumns=@JoinColumn(name="project_id")
    )
    private List<PersonData> employees;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name="patent_project",
            inverseJoinColumns  = @JoinColumn(name="patent_id"),
            joinColumns=@JoinColumn(name="project_id")
    )
    private List<Patent> patents;


}
