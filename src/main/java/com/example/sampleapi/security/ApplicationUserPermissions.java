package com.example.sampleapi.security;

public enum ApplicationUserPermissions {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write"),
    PERSON_READ("person:read"),
    PERSON_WRITE("person:write");

    private final String permission;
    ApplicationUserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermissionName(){
        return this.permission;
    }
}
