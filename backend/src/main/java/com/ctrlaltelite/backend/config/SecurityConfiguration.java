package com.ctrlaltelite.backend.config;

import com.ctrlaltelite.backend.services.JpaUserDetailsService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JpaUserDetailsService jpaUserDetailsService;

  private final JwtAuthFilter jwtAuthFilter;
  private MessageSource messageSource;

  public SecurityConfiguration(JpaUserDetailsService jpaUserDetailsService, JwtAuthFilter jwtAuthFilter) {
    this.jpaUserDetailsService = jpaUserDetailsService;
    this.jwtAuthFilter = jwtAuthFilter;
  }

  @Bean
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(jpaUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(jpaUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    AuthenticationManager authenticationManager = new ProviderManager(Collections.singletonList(authProvider));

    return new AuthenticationManager() {
      @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
          return authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {


          throw new UsernameNotFoundException(messageSource.getMessage("config.wrongPwOrUsername", null, LocaleContextHolder.getLocale()));
        }
      }
    };
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //responsible for all the HTTp security of our app
    http
        .csrf()
        .disable()
        .authorizeHttpRequests()
//        .requestMatchers("/api/v1/auth/**")
            .requestMatchers("/hello").permitAll()//whitelisting above, what is in requestMAtchers needs to be authenticated, everything else is free from auth
            .requestMatchers("/login").permitAll()
            .requestMatchers("/register").permitAll()
            .requestMatchers("/confirm/**").permitAll()
            .requestMatchers("/reset/**").permitAll()
            .requestMatchers("/request/tracked/period").permitAll()
            .requestMatchers("/uploads/**").permitAll()
            .requestMatchers("/populate").permitAll()
            .anyRequest()
        .authenticated()
        .and()//for to add new config
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//spring will create new session for each request
        .and()
        .authenticationProvider(authenticationProvider()) //which authentication provider I want to use (Bean in ApplicationConfig)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // execute jwtAuthFilter before user UPAF

    return http.build();
  }
}
