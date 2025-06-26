package com.beanFactory.toursTravels.mapper;

import com.beanFactory.toursTravels.dto.UserRegisterDto;
import com.beanFactory.toursTravels.model.TourUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel =  "spring")
public interface UserMapper {

    @Mapping(source="userName", target="username")
    TourUser toEntity(UserRegisterDto dto);

    @Mapping(source = "username", target="userName")
    UserRegisterDto toDto(TourUser tourUser);
}
