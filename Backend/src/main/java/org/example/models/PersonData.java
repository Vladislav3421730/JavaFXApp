package org.example.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.models.enums.Gender;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "persondata")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonData  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String surname;
    private BigDecimal salary;
    private String email;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String position;
}
