package com.proof.events_system.service.implement;

import com.proof.events_system.domain.entity.UserEntity;
import com.proof.events_system.dto.UserEntityDTO;
import com.proof.events_system.exception.ApiError;
import com.proof.events_system.exception.EventsException;
import com.proof.events_system.repository.IUserRepository;
import com.proof.events_system.service.interfaces.IUserService;
import com.proof.events_system.util.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final IUserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(IUserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntityDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("User not found with id: {}", id);
                    return new EventsException(ApiError.USER_NOT_FOUND);
                });
        return userMapper.toUserDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserEntityDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) {
            LOGGER.info("User list is empty");
            return Collections.emptyList();
        }
        return userMapper.toListUserDTO(users);
    }

    @Transactional
    @Override
    public void registerUser(UserEntityDTO userDTO) {
        UserEntity user = userMapper.toUser(userDTO);
        Objects.requireNonNull(user);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserEntityDTO updateUser(Long id, UserEntityDTO userDTO) {
        UserEntity userFind = userRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("User to updating not found with id: {}", id);
                    return new EventsException(ApiError.USER_NOT_FOUND);
                });
        UserEntity userUpdated = this.updateFields(userFind, userDTO);
        userRepository.save(userUpdated);
        return userMapper.toUserDTO(userUpdated);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("User to deleting not found with id: {}", id);
                    return new EventsException(ApiError.USER_NOT_FOUND);
                });
        userRepository.delete(user);
    }

    private UserEntity updateFields(UserEntity user, UserEntityDTO userDTO) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRole(user.getRole());
        return user;
    }
}
