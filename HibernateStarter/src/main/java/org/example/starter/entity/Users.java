package org.example.starter.entity;
import lombok.*;
import org.example.starter.entity.enumerat.Role;
import org.example.starter.entity.parametrs.PersonalInfo;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "company")
@Builder
@Entity
@EqualsAndHashCode(of = "username")
@Table(name = "Users", schema = "public")
public class Users{
    @Id
    @GeneratedValue(generator = "user_gen",strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String username;
    @Embedded
    private PersonalInfo personalInfo;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;
}
