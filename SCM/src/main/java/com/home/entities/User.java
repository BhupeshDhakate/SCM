package com.home.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{

    @Id
    private String userId;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Getter(AccessLevel.NONE)
    private String password;
    @Column(length = 1000)
    private String about;
    @Column(length = 1000)
    private String profilePic;
    private String phoneNumber;

    @Getter(AccessLevel.NONE)
    // information
    private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    @Enumerated(value = EnumType.STRING)
    // self, google, facebook, twitter, linkdin, github
    private providers provider = providers.self;
    private String providerUserId;

    // add more fields if needed
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact>contacts= new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    private String emailToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //list of roles[USER, ADMIN]
        //collection of SimpGranredAuthority[role(ADMIN, USER)]
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }

    // in this project our email id is our username
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isEnabled(){
        return this.enabled;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    
}
