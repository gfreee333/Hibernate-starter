package org.example.starter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(of = "name")
@ToString(exclude = "users")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Users> users = new HashSet<>();
    public void addUsers(Users user){
    users.add(user);
    user.setCompany(this);
    }
}
