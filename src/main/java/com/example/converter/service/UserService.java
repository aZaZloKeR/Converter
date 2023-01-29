package com.example.converter.service;

import com.example.converter.db.entities.Role;
import com.example.converter.db.entities.User;
import com.example.converter.db.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepo.findByLogin(login);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException(String.format("user %s not found",login));
        }
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthoritiesFromUserRoles(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> getAuthoritiesFromUserRoles(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
    public User getUserByLogin(String login){
        Optional<User> byLogin = userRepo.findByLogin(login);
        return byLogin.orElse(null);
    }
}
