package com.beanFactory.toursTravels.service;

import com.beanFactory.toursTravels.model.Tour;
import com.beanFactory.toursTravels.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    @Autowired
    private TourRepository repo;

    public List<Tour> getAllTours(){
        return repo.findAll();
    }

    public Optional<Tour> getTourById(Long id){
        return repo.findById(id);
    }

    public Tour createTour(Tour tour){
        return repo.save(tour);
    }

    public List<Tour> addMultipleTours(List<Tour> tours){
        return repo.saveAll(tours);
    }

    public void deleteTourById(Long id){
        repo.deleteById(id);
    }

    public Optional<Tour> updateTour(Long id, Tour updatedTour){
        return repo.findById(id).map(tour -> {
            tour.setName(updatedTour.getName());
            tour.setDescription(updatedTour.getDescription());
            tour.setPrice(updatedTour.getPrice());
            tour.setTicketsAvailable(updatedTour.getTicketsAvailable());
            tour.setAdditionalDetails(updatedTour.getAdditionalDetails());
            return repo.save(tour);
        });
    }


}
