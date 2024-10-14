package org.example.models;



import com.google.gson.annotations.Expose;
import lombok.*;
import org.example.models.enums.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String login;
    private String password;
    private boolean isBun;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles=new HashSet<>();

    @OneToOne(cascade = {CascadeType.REMOVE,CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name = "person_data_id",nullable = false)
    private PersonData personData;

}
