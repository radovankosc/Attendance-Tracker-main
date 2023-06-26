package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.models.dao.PWResetRequestData;
import com.ctrlaltelite.backend.repositories.UserRepository;
import com.ctrlaltelite.backend.utilities.GenerateAlphanumericCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResetPasswordService {
    private final UserRepository userRepository;

    private final EmailSenderService emailService;

    private final PasswordEncoder encoder;
    private final MessageSource messageSource;


    public ResetPasswordService(UserRepository userRepository, EmailSenderService emailService, PasswordEncoder encoder, @Qualifier("messageSource") MessageSource messageSource) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.encoder = encoder;
        this.messageSource = messageSource;
    }

    public Optional<AppUser> sendResetCode(PWResetRequestData pwResetRequestData) {
        String resetCode = GenerateAlphanumericCode.generate(48);
        System.out.println(resetCode);
        Optional<AppUser> user;

        if (pwResetRequestData.getUsername() != null) {
            user = userRepository.findByUsername(pwResetRequestData.getUsername());

        } else {
            user = userRepository.findByEmail(pwResetRequestData.getEmail());
        }

        user.get().setPwResetCode(resetCode);
        userRepository.save(user.get());
        String emailSubject = messageSource.getMessage("reset.emailSubject", null, LocaleContextHolder.getLocale());
        String emailBody = messageSource.getMessage("reset.emailBody", null, LocaleContextHolder.getLocale());
        emailService.sendEmail(user.get().getEmail(), emailSubject,
                emailBody + resetCode);
        return user;
    }


    public Boolean validatePassword(String resetCode, String password) {
        Optional<AppUser> user = userRepository.findByPwResetCode(resetCode);

        if (!user.isPresent()) {
            return false;
        }

        AppUser currentUser = user.get();
        currentUser.setPassword(encoder.encode(password));
        currentUser.setPwResetCode(null);
        userRepository.save(currentUser);

        return true;
    }
}
