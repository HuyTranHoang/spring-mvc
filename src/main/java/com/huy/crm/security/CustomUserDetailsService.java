package com.huy.crm.security;

import com.huy.crm.dao.UserDAO;
import com.huy.crm.entity.Role;
import com.huy.crm.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        UserEntity userEntity = userDAO.findByUserName(username);
        return buildUserForAuthentication(userEntity, userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList()));
    }

    private User buildUserForAuthentication(UserEntity user,
                                            List<GrantedAuthority> authorities) {
        String username = user.getUsername();
        String password = user.getPassword();
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        CurrentUser currentUser = new CurrentUser(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);

        currentUser.setImageUrl(user.getImageUrl());
        return currentUser;
    }
}
