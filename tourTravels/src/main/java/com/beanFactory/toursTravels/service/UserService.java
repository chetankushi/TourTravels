package com.beanFactory.toursTravels.service;

import com.beanFactory.toursTravels.model.TourUser;
import com.beanFactory.toursTravels.dto.UserRegisterDto;
import com.beanFactory.toursTravels.dto.UserLoginDto;

import java.util.List;

public interface UserService  {

    UserRegisterDto registerUser(UserRegisterDto user);
    UserLoginDto loginUser(UserLoginDto user);
    TourUser findUserByUsername(String username);
    Boolean existsByUsername(String username);

    List<TourUser> findAllUsers();

    String deleteUser(long id);
    UserRegisterDto updateUser(UserRegisterDto user);
}
