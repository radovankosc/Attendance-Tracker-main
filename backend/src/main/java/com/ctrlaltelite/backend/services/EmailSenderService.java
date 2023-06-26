package com.ctrlaltelite.backend.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private final MessageSource messageSource;
    private JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender, @Qualifier("messageSource") MessageSource messageSource) {
        this.mailSender = mailSender;
        this.messageSource = messageSource;
    }

    public void sendEmail(String receiver, String subject, String emailBody){

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("atndnctracker@gmail.com");
        email.setTo(receiver);
        email.setSubject(subject);
        email.setText(emailBody);

        mailSender.send(email);

        System.out.println(messageSource.getMessage("emailSender.success", null, LocaleContextHolder.getLocale()));
    }
}
