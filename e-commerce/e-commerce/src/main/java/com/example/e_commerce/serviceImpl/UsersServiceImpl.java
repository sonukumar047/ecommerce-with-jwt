package com.example.e_commerce.serviceImpl;

import com.example.e_commerce.entity.Role;
import com.example.e_commerce.entity.Users;
import com.example.e_commerce.repository.UsersRepository;
import com.example.e_commerce.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsersServiceImpl implements UsersService, UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users createUsers(Users users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        if(users.getRole() == null){
            users.setRole(Role.USER);
        };
        return usersRepository.save(users);
    }

    @Override
    public Users getUsersByUsername(String username){
        return usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        System.out.println("**********" + user.getRole().name());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

}
