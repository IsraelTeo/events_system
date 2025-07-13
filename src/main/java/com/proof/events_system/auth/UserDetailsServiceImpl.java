package com.proof.events_system.auth;

import com.proof.events_system.domain.entity.UserEntity;
import com.proof.events_system.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final IUserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(()->{
            LOGGER.error("User with email {} not found ", email);
            return new UsernameNotFoundException(email);
        });

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String roleName = "ROLE_" + user.getRole().getNameRole();
        authorities.add(new SimpleGrantedAuthority(roleName));

        return new User(
                user.getEmail(),
                user.getPassword(),
                !user.getEnabled(),
                !user.getAccountNonExpired(),
                !user.getCredentialsNonExpired(),
                !user.getAccountNonLocked(),
                authorities
        );
    }


}
