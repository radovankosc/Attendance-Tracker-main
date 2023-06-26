package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {

        public final UserRepository userRepository;

        public AdminService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public boolean isAdmin(String username){
            Optional<AppUser> myUser = userRepository.findByUsername(username);
            AppUser myObj = myUser.get();
            Set roles = myObj.getRoles();
            if(roles.contains("ROLE_ADMIN")){
                return true;
            }
            return false;
        }
}
