package com.example.taskmanager.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor
    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    // Implementing methods from UserDetails

    @Override
    public String getUsername() {
        return username;
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
        return true; // You can adjust this logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Adjust as necessary
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust as necessary
    }

    @Override
    public boolean isEnabled() {
        return true; // Adjust as necessary
    }
}