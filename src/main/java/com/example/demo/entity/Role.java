package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy="role")
    private List<User> users;
    @Override
    public String getAuthority() {
        return getName();
    }
    public static Role getRoleById(Integer id) {
        switch (id) {
            case 1: return new Role(1,"admin",null);
            case 2: return new Role(2,"reader",null);
            default: return null;
        }
    }
}
