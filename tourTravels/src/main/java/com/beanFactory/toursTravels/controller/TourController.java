package com.beanFactory.toursTravels.controller;

import com.beanFactory.toursTravels.model.Tour;
import com.beanFactory.toursTravels.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST})
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping("/tours")
    public List<Tour> getAllTours(){
        return  tourService.getAllTours();
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(CsrfToken token){
        return token;
    }

    @PostMapping("/admin/tours")
    public ResponseEntity<Tour> addTour(@RequestBody Tour tour){
        return new ResponseEntity<>(tourService.createTour(tour), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/tours/{tourId}")
    public ResponseEntity<String> deleteTour(@PathVariable Long tourId){
        Optional<Tour> foundTour = tourService.getTourById(tourId);
        if(foundTour.isPresent()){
            tourService.deleteTourById(tourId);
            return new ResponseEntity<>(("Deleted Tour of " + tourId), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Tour not found to delete.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/admin/tour")
    public ResponseEntity<?> updateTour(@RequestBody Tour tour){
        Optional<Tour> foundTour = tourService.getTourById(tour.getId());
        if(foundTour.isPresent()){
            tourService.updateTour(tour.getId(), tour);
            return new ResponseEntity<Tour>(tour, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Tour not found to update.", HttpStatus.NOT_FOUND);
    }

}
