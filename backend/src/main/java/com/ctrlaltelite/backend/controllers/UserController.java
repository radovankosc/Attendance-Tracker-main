package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.config.JwtService;
import com.ctrlaltelite.backend.exceptions.ValidationException;
import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.models.dao.AuthRequest;

import com.ctrlaltelite.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MessageSource messageSource;

    public UserController(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, @Qualifier("messageSource") MessageSource messageSource) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.messageSource = messageSource;
    }

    @PostMapping("/login")
    public ResponseEntity<String> sendJwtToken(@RequestBody AuthRequest authRequest) throws Exception {
    String username = authRequest.getUsername();
    String password = authRequest.getPassword();

    if (!isPostedDataValid(username, password))
        return ResponseEntity.status(400).body(messageSource.getMessage("err.loginProvideCredentials", null, LocaleContextHolder.getLocale()));

    Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username,password));
          if (authentication.isAuthenticated())
            return ResponseEntity.ok().body(jwtService.generateToken(authRequest.getUsername()));
          throw new ValidationException(messageSource.getMessage("err.incorrectCredentials", null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Iterable<AppUser> findAll(){ //interation through all of users
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AppUser findById(@PathVariable("id") AppUser appUser){ //assign using domain class converter, it gets repository by Id
        return appUser;
    }

    private boolean isPostedDataValid(String username, String password){
        return (isValid(username) && isValid(password));
    }
    private boolean isValid(String string){
        return (string != null && !string.isEmpty());
    }

}