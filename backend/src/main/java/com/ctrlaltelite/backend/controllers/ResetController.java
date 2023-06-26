package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.models.dao.PWResetRequestData;
import com.ctrlaltelite.backend.repositories.UserRepository;
import com.ctrlaltelite.backend.services.EmailSenderService;
import com.ctrlaltelite.backend.services.ResetPasswordService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class ResetController {
    private final ResetPasswordService resetPasswordService;

    public final EmailSenderService emailSenderService;
    private final MessageSource messageSource;

    public ResetController(ResetPasswordService resetPasswordService, EmailSenderService emailSenderService, UserRepository userRepository, @Qualifier("messageSource") MessageSource messageSource) {
        this.resetPasswordService = resetPasswordService;
        this.emailSenderService = emailSenderService;
        this.messageSource = messageSource;
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PWResetRequestData data) {
        if (data.getUsername() == null && data.getEmail() == null) {
            String message = messageSource.getMessage("reset.provideUsernameOrEmail", null, LocaleContextHolder.getLocale());
            return ResponseEntity.badRequest().body(message);
        }
        resetPasswordService.sendResetCode(data);
        String userMail = resetPasswordService.sendResetCode(data).get().getEmail();
        String message = messageSource.getMessage("reset.codeSentSuccess", null, LocaleContextHolder.getLocale());
        return ResponseEntity.ok().body(message + userMail + " .");
    }

    @PostMapping("/reset/{resetCode}")
    public ResponseEntity<String> updatePassword(@PathVariable String resetCode, @RequestBody Map<String, String> requestParams) {
        String password = requestParams.get("password");

        if (password == null || password.isEmpty()) {
            String message = messageSource.getMessage("reset.providePassword", null, LocaleContextHolder.getLocale());
            return ResponseEntity.badRequest().body(message);
        }

        boolean success = resetPasswordService.validatePassword(resetCode, password); // Update the password

        if (success) {
            String message = messageSource.getMessage("reset.passwordUpdatedSuccess", null, LocaleContextHolder.getLocale());
            return ResponseEntity.ok().body(message);
        } else {
            String message = messageSource.getMessage("reset.invalidResetCode", null, LocaleContextHolder.getLocale());
            return ResponseEntity.badRequest().body(message);
        }
    }
}