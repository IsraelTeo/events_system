package com.proof.events_system.dto;

import com.proof.events_system.domain.enums.Role;
import jakarta.validation.constraints.NotNull;

public class RoleEntityDTO {

    @NotNull(message = "The role name cannot be null")
    private Role nameRole;

    public RoleEntityDTO() {
    }

    public RoleEntityDTO(Role nameRole) {
        this.nameRole = nameRole;
    }

    public Role getNameRole() {
        return nameRole;
    }

    public void setNameRole(Role nameRole) {
        this.nameRole = nameRole;
    }
}
