package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.models.dao.DaoAppUser;
import com.ctrlaltelite.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {
    public final UserRepository userRepository;

    public UserProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public DaoAppUser getUserProfile(String username){
        Optional<AppUser> myUser = userRepository.findByUsername(username);
        AppUser myObj = myUser.get();
        DaoAppUser daoAppUser = new DaoAppUser(myObj.getId(), myObj.getUsername(), myObj.getEmail(), myObj.getRoles(), myObj.getWorkSchedule());
        return daoAppUser;
    }
}
