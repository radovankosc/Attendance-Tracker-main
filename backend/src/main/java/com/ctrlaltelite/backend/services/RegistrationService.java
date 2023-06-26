package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.dto.UserDto;
import com.ctrlaltelite.backend.exceptions.ValidationException;
import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.repositories.UserRepository;
import com.ctrlaltelite.backend.utilities.GenerateAlphanumericCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;

    private final EmailSenderService emailService;

    private final PasswordEncoder encoder;
    private final MessageSource messageSource;

    public RegistrationService(UserRepository userRepository, EmailSenderService emailService, PasswordEncoder encoder, @Qualifier("messageSource") MessageSource messageSource) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.encoder = encoder;
        this.messageSource = messageSource;
    }

    public void register(UserDto user) throws ValidationException {
        if (user.getUsername() == null || !StringUtils.hasText(user.getUsername())){

            throw new ValidationException(messageSource.getMessage("register.err.emptyUsername", null, LocaleContextHolder.getLocale()));
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException(messageSource.getMessage("register.err.userExists", null, LocaleContextHolder.getLocale()));
        }
        if (user.getPassword() == null || !StringUtils.hasText(user.getPassword())){
            throw new ValidationException(messageSource.getMessage("register.err.emptyPassword", null, LocaleContextHolder.getLocale()));
        }
        if (user.getEmail() == null || !StringUtils.hasText(user.getEmail())){
            throw new ValidationException(messageSource.getMessage("register.err.emptyEmail", null, LocaleContextHolder.getLocale()));
        }
        if (user.getPassword().length() <= 8 ){
            throw new ValidationException(messageSource.getMessage("register.err.passwordShorter", null, LocaleContextHolder.getLocale()));
        }
        AppUser newUser = new AppUser(null, user.getEmail(),user.getUsername(),encoder.encode(user.getPassword()),null, false, null, null, "8888800" );


        String activationCode = GenerateAlphanumericCode.generate(48);
        String activationCodeMsg =messageSource.getMessage("register.activationCode", null, LocaleContextHolder.getLocale());
        System.out.println(activationCodeMsg + activationCode);

        newUser.setActivationCode(activationCode);
        userRepository.save(newUser);

        String emailSubject = messageSource.getMessage("register.emailSubject", null, LocaleContextHolder.getLocale());
        String emailBody = messageSource.getMessage("register.emailBody", null, LocaleContextHolder.getLocale());

        emailService.sendEmail
                (newUser.getEmail(),emailSubject, emailBody + activationCode);
    }

    public void activate(String username, String activationCode) throws ValidationException {
        Optional<AppUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ValidationException(messageSource.getMessage("register.activate.err.noUser", null, LocaleContextHolder.getLocale()));
        }
        if (user.get().getActive()) {
            throw new ValidationException(messageSource.getMessage("register.activate.err.userActive", null, LocaleContextHolder.getLocale()));
        }
        if (!activationCode.equals(user.get().getActivationCode())){
            throw new ValidationException(messageSource.getMessage("register.activate.err.wrongCode", null, LocaleContextHolder.getLocale()));
        }

        AppUser activatedUser = user.get();
        activatedUser.setActive(true);
        activatedUser.setActivationCode(null);

        userRepository.save(activatedUser);

    }
}
