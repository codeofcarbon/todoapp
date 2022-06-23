package com.codeofcarbon.todoapp.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "users")
//@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank
    private String userLogin;
    @NotBlank
    @Size(min = 8, message = "password must have at least 8 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Task> tasks = new HashSet<>();
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();


    public User(String userLogin, String password) {            // todo
        this.userLogin = userLogin;
        this.password = password;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userLogin;
    }

    public void grantAuthority(Role role) {
        roles.add(role);
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
