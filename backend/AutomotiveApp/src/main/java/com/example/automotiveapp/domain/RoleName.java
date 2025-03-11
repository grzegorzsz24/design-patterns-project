package com.example.automotiveapp.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Getter
@Embeddable
public class RoleName {

    private String name;

    private String description;

    public static final RoleName USER_ROLE = new RoleName("USER", "Standard user role");
    public static final RoleName ADMIN_ROLE = new RoleName("ADMIN", "Admin role");

    protected RoleName() {}

    public RoleName(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RoleName clone() {
        return new RoleName(this.name, this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleName roleName)) return false;
        return Objects.equals(name, roleName.name) &&
                Objects.equals(description, roleName.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
