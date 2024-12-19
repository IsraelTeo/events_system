package com.proof.events_system.util.mapper;

import com.proof.events_system.domain.entity.UserEntity;
import com.proof.events_system.dto.UserEntityDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserEntityDTO toUserDTO(UserEntity user) {
        return modelMapper.map(user, UserEntityDTO.class);
    }

    public UserEntity toUser(UserEntityDTO user) {
        return modelMapper.map(user, UserEntity.class);
    }

    public List<UserEntityDTO> toListUserDTO(List<UserEntity> users) {
        return users.stream()
                .map(this::toUserDTO)
                .toList();
    }
}
