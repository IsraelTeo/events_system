package com.proof.events_system.domain.entity;

import com.proof.events_system.domain.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role nameRole;

    public RoleEntity() {
    }

    public RoleEntity(Long id, Role nameRole) {
        this.id = id;
        this.nameRole = nameRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getNameRole() {
        return nameRole;
    }

    public void setNameRole(Role nameRole) {
        this.nameRole = nameRole;
    }
}
