package com.example.converter.service;

import com.example.converter.db.entities.Role;
import com.example.converter.db.entities.User;
import com.example.converter.db.repositories.RoleRepo;
import com.example.converter.db.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationServiceBean implements RegistrationService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean registerUser(String login, String password) {
        if(userRepo.findByLogin(login).isEmpty()){
            Optional<Role> role = roleRepo.findByName("ROLE_USER");
            if (role.isPresent()) {
                User user = new User();
                user.setRoles(List.of(role.get()));
                user.setLogin(login);
                user.setPassword(passwordEncoder.encode(password));
                userRepo.save(user);
                return true;
            }
        }
        return false;
    }
}
