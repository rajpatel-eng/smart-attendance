package com.capstoneproject.smartattendance.security;

import com.capstoneproject.smartattendance.entity.User;
import com.capstoneproject.smartattendance.repository.UserRepo;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("USER_NOT_FOUND"));

        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
                

        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPassword(),
                Collections.singletonList(authority)  
        );
    }
    
}
