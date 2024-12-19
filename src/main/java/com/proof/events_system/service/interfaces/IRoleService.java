package com.proof.events_system.service.interfaces;

import com.proof.events_system.dto.RoleEntityDTO;

import java.util.List;

public interface IRoleService {

    RoleEntityDTO getRoleById(Long id);

    List<RoleEntityDTO> getAllRoles();

    void saveRole(RoleEntityDTO roleDTO);

    RoleEntityDTO updateRole(Long id,  RoleEntityDTO roleDTO);

    void deleteRole(Long id);
}
