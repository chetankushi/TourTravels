package com.beanFactory.toursTravels.service;

import com.beanFactory.toursTravels.model.TourUser;
import com.beanFactory.toursTravels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user by username...");
        TourUser tourUser = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(tourUser.getUsername())
                .password(tourUser.getPassword())
                .roles(tourUser.getRole().replace("ROLE_", ""))
                .build();
    }
}
