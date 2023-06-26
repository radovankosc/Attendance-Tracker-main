package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.models.SecurityUser;
import com.ctrlaltelite.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository; //instance of UserRepo
    private final MessageSource messageSource;

    public JpaUserDetailsService(UserRepository userRepository, @Qualifier("messageSource") MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //UserDetails is core user info
//        //validation:
//        return userRepository
//                .findByUsername(username)
//                .map(SecurityUser::new) //if you find some user map it to the Security user
//                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //UserDetails is core user info
        //validation:
        if (userRepository.findByUsername(username).isPresent())
            return userRepository
                    .findByUsername(username)
                    .map(SecurityUser::new)
                    .orElseThrow(()-> new UsernameNotFoundException(messageSource.getMessage("jpa.err.userNotFound", null, LocaleContextHolder.getLocale()) + username));

        return userRepository
                .findByEmail(username)
                .map(SecurityUser::new) //if you find some user map it to the Security user
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("jpa.err.userNotFound", null, LocaleContextHolder.getLocale()) + username));
    }
}
