package com.spring.demo.dto;

import com.spring.demo.entity.UserRole;

public record RegisterNewUserDto(String login, String password, UserRole role) {

}
