package com.sysc4806.project.models;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN,
    MEMBER;

    public String getAuthority()
    {
        return this.name();
    }
}
