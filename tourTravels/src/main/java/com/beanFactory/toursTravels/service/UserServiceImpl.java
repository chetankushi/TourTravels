package com.beanFactory.toursTravels.service;

import com.beanFactory.toursTravels.dto.UserRegisterDto;
import com.beanFactory.toursTravels.mapper.UserMapper;
import com.beanFactory.toursTravels.model.TourUser;
import com.beanFactory.toursTravels.dto.UserLoginDto;
import com.beanFactory.toursTravels.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserRegisterDto registerUser(UserRegisterDto user) {
        TourUser savedTourUser = userMapper.toEntity(user);
        savedTourUser.setCreatedDate(LocalDateTime.now());
        savedTourUser.setModifiedDate(LocalDateTime.now());
        savedTourUser.setRole("USER");
        savedTourUser.setPassword(passwordEncoder.encode(user.getPassword()));

        TourUser registeredTourUser = userRepository.save(savedTourUser);
        return userMapper.toDto(registeredTourUser);
    }

    @Override
    public UserLoginDto loginUser(UserLoginDto user) {
        return null;
    }

    @Override
    public TourUser findUserByUsername(String username) {
        return null;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<TourUser> findAllUsers() {
        return List.of();
    }

    @Override
    public String deleteUser(long id) {
        return "";
    }

    @Override
    public UserRegisterDto updateUser(UserRegisterDto user) {
        return null;
    }
}
