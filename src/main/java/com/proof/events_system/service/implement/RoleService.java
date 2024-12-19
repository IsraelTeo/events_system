package com.proof.events_system.service.implement;

import com.proof.events_system.domain.entity.RoleEntity;
import com.proof.events_system.dto.RoleEntityDTO;
import com.proof.events_system.exception.ApiError;
import com.proof.events_system.exception.EventsException;
import com.proof.events_system.repository.IRoleRepository;
import com.proof.events_system.service.interfaces.IRoleService;
import com.proof.events_system.util.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class RoleService implements IRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    private final IRoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Autowired
    public RoleService(IRoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public RoleEntityDTO getRoleById(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Role not found with id: {}", id);
                    return new EventsException(ApiError.ROLE_NOT_FOUND);
                });
        return roleMapper.toRoleDTO(role);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleEntityDTO> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            LOGGER.info("Role list is empty");
            return Collections.emptyList();
        }
        return roleMapper.toListRoleDTO(roles);
    }

    @Transactional
    @Override
    public void saveRole(RoleEntityDTO roleDTO) {
        RoleEntity role = roleMapper.toRole(roleDTO);
        Objects.requireNonNull(role);
        roleRepository.save(role);
    }

    @Transactional
    @Override
    public RoleEntityDTO updateRole(Long id, RoleEntityDTO roleDTO) {
        RoleEntity roleFind = roleRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Role to updating not found with id: {}", id);
                    return new EventsException(ApiError.ROLE_NOT_FOUND);
                });
        RoleEntity roleUpdated = this.updateFields(roleFind, roleDTO);
        roleRepository.save(roleUpdated);
        return roleMapper.toRoleDTO(roleUpdated);
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Role to deleting not found with id: {}", id);
                    return new EventsException(ApiError.ROLE_NOT_FOUND);
                });
        roleRepository.delete(role);
    }

    private RoleEntity updateFields(RoleEntity role, RoleEntityDTO roleDTO) {
        role.setNameRole(roleDTO.getNameRole());
        return role;
    }
}
