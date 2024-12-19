package com.proof.events_system.util.mapper;

import com.proof.events_system.domain.entity.RoleEntity;
import com.proof.events_system.dto.RoleEntityDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleEntityDTO toRoleDTO(RoleEntity role) {
        return modelMapper.map(role, RoleEntityDTO.class);
    }

    public RoleEntity toRole(RoleEntityDTO roleDTO) {
        return modelMapper.map(roleDTO, RoleEntity.class);
    }

    public List<RoleEntityDTO> toListRoleDTO(List<RoleEntity> roles) {
        return roles.stream()
                .map(this::toRoleDTO)
                .toList();
    }
}
