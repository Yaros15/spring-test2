package com.example.springtest2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usr")
public class User implements UserDetails { // интерфейс сведений о пользователе ядря безопасности

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String name, String firstname, String lastname, String email, String password, Role role) {
        this.name = name;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // наюор предоставленных полномочий
        return List.of(new SimpleGrantedAuthority(role.name())); // возвращает список ролей
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // срок действия ааккаунта не истек

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // не заблакирован

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // не зарегестрирован по истечению срока действия

    @Override
    public boolean isEnabled() {
        return true;
    } //включен
}
