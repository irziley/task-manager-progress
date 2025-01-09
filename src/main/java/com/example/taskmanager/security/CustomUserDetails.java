package com.example.taskmanager.security;  // or com.example.taskmanager.service depending on where you place it

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.taskmanager.model.User;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    // Constructor to create CustomUserDetails from User entity
    public CustomUserDetails(String email, String password, List<GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Factory method to convert User entity to CustomUserDetails
    public static CustomUserDetails fromUserEntityToCustomUserDetails(User user) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));  // Example: Add roles
        return new CustomUserDetails(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Adjust logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Adjust logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Adjust logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true;  // Adjust logic if needed
    }
}