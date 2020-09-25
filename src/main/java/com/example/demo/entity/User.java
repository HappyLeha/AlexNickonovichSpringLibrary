package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="login",unique = true)
    private String login;
    @Column(name="password")
    private String password;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="year")
    private Integer year;
    @Column(name="email")
    private String email;
    @Column(name="phone")
    private String phone;
    @Lob
    @Column(name="photo", length=65535)
    private String photo;
    @OneToOne(mappedBy = "user")
    private Rent rent;
    @ManyToOne
    @JoinColumn(name="role")
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Role> list=new ArrayList<Role>();
        list.add(role);
        return list;
    }
    @Override
    public String getUsername() {
        return login;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
