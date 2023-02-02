package com.example.sampleapi.security;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRoles {
    STUDENT(new HashSet<ApplicationUserPermissions>(Arrays.asList(
            ApplicationUserPermissions.STUDENT_READ
    ))),
    ADMINTRAINEE(new HashSet<ApplicationUserPermissions>(Arrays.asList(
            ApplicationUserPermissions.STUDENT_READ,
            ApplicationUserPermissions.COURSE_READ,
            ApplicationUserPermissions.PERSON_READ
    ))),
    ADMIN(new HashSet<ApplicationUserPermissions>(Arrays.asList(
            ApplicationUserPermissions.STUDENT_READ,
            ApplicationUserPermissions.STUDENT_WRITE,
            ApplicationUserPermissions.COURSE_READ,
            ApplicationUserPermissions.COURSE_WRITE,
            ApplicationUserPermissions.PERSON_READ,
            ApplicationUserPermissions.PERSON_WRITE
    )));

    private final Set<ApplicationUserPermissions> permissions;

    ApplicationUserRoles(Set<ApplicationUserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermissions> getPermissions(){
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> authorities = this.permissions
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermissionName()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
