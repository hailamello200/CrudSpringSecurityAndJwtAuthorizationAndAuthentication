package com.spring.demo.controllers;

import com.spring.demo.dto.AuthenticationDto;
import com.spring.demo.dto.LoginResponseDto;
import com.spring.demo.dto.RegisterNewUserDto;
import com.spring.demo.entity.User;
import com.spring.demo.repository.UserRepository;
import com.spring.demo.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService serviceToken;

    @PostMapping("/login")
    public ResponseEntity loginAuthentication(@RequestBody @Valid AuthenticationDto dataDto) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(dataDto.login(), dataDto.password());
        var authenticationUser = this.manager.authenticate(userNamePassword);

        var token = serviceToken.generateToken((User) authenticationUser.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid RegisterNewUserDto data) {
        if (this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String passwordEncrypted = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), passwordEncrypted, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
