package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy="role")
    @ToString.Exclude
    private List<User> users;

    @Override
    public String getAuthority() {
        return getName();
    }

}
