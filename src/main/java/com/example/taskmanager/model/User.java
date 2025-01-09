package com.example.taskmanager.model;

import jakarta.persistence.*;  // Use jakarta.persistence instead of javax.persistence
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @OneToMany(mappedBy = "author")
    private Collection<Task> tasks;

    @OneToMany(mappedBy = "executor")
    private Collection<Task> assignedTasks;

    @ElementCollection
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Collection<String> roles;

    public User() {
        // Default constructor
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the roles collection to a set of authorities (GrantedAuthority objects)
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String role : roles) {
            authorities.add(() -> role);  // Use lambda expression to return the role as a GrantedAuthority
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;  // In this case, the username is the email address
    }

    @Override
    public String getPassword() {
        return password;  // Return the password
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Assuming the account is not expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Assuming the account is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Assuming the credentials are not expired
    }

    @Override
    public boolean isEnabled() {
        return true;  // Assuming the account is enabled
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }

    public Collection<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(Collection<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    // New methods for setting password and role
    public void setPassword(String password) {
        this.password = password;  // Sets the password
    }

    public void setRole(String role) {
        this.roles.add(role);  // Adds a role to the user's roles collection
    }
}
