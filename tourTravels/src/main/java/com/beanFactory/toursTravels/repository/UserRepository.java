package com.beanFactory.toursTravels.repository;


import com.beanFactory.toursTravels.model.TourUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TourUser, Long> {
    Optional<TourUser> findByUsername(String username);

    Boolean existsByUsername(String username);
}
