package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.models.dao.DaoChangeApproverRequest;
import com.ctrlaltelite.backend.repositories.UserRepository;
import com.ctrlaltelite.backend.services.AdminService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    private final AdminService adminService;

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public AdminController(AdminService adminService,
                           UserRepository userRepository, @Qualifier("messageSource") MessageSource messageSource) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @PatchMapping("/change-approver")
    public String changeSupervisor(@RequestBody DaoChangeApproverRequest daoChangeApproverRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        if(adminService.isAdmin(currentUser)){
            AppUser editingUser = userRepository.getAppUserByIdIs(daoChangeApproverRequest.getUserId()).get();
            editingUser.setApproverId(daoChangeApproverRequest.getApproverId());
            userRepository.save(editingUser);
            return messageSource.getMessage("admin.changeSuccess", null, LocaleContextHolder.getLocale());
        }
        return messageSource.getMessage("admin.changeSuccessNot", null, LocaleContextHolder.getLocale());
    }
}
