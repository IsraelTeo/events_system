package com.proof.events_system.service.interfaces;

import com.proof.events_system.dto.UserEntityDTO;

import java.util.List;

public interface IUserService {

    UserEntityDTO getUserById(Long id);

    List<UserEntityDTO> getAllUsers();

    void registerUser(UserEntityDTO userDTO);

    UserEntityDTO updateUser(Long id,  UserEntityDTO userDTO);

    void deleteUser(Long id);
}
