package com.huy.crm.security;

import com.huy.crm.dao.UserDAO;
import com.huy.crm.entity.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDAO.findByUserName(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User authUser = new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getUserRoles().stream()
                        .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getName()))
                        .collect(Collectors.toList())
        );

        return authUser;
    }
}
