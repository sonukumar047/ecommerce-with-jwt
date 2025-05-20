package com.example.e_commerce.service;

import com.example.e_commerce.entity.Users;

public interface UsersService {
    Users createUsers(Users users);
    Users getUsersByUsername(String username);
}
