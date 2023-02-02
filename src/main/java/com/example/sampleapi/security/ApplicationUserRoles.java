package com.example.sampleapi.security;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum ApplicationUserRoles {
    STUDENT(new HashSet<ApplicationUserPermissions>(Arrays.asList(
            ApplicationUserPermissions.STUDENT_READ,
            ApplicationUserPermissions.PERSON_WRITE
    ))),
    ADMIN(new HashSet<ApplicationUserPermissions>(Arrays.asList(
            ApplicationUserPermissions.STUDENT_READ,
            ApplicationUserPermissions.STUDENT_WRITE,
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
}
