package com.shiva.trainbookingbackend.service;

import com.shiva.trainbookingbackend.entity.Train;
import com.shiva.trainbookingbackend.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {

    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    // Add a new train
    public Train addTrain(Train train) {
        return trainRepository.save(train);
    }

    // Get all trains
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    // Get train by ID
    public Train getTrainById(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found with ID: " + id));
    }

    // Update train details
    public Train updateTrain(Long id, Train updatedTrain) {

        Train existingTrain = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found with ID: " + id));

        existingTrain.setTrainNumber(updatedTrain.getTrainNumber());
        existingTrain.setTrainName(updatedTrain.getTrainName());
        existingTrain.setSource(updatedTrain.getSource());
        existingTrain.setDestination(updatedTrain.getDestination());
        existingTrain.setTotalSeats(updatedTrain.getTotalSeats());
        existingTrain.setAvailableSeats(updatedTrain.getAvailableSeats());

        return trainRepository.save(existingTrain);
    }

    // Delete train
    public void deleteTrain(Long id) {

        if (!trainRepository.existsById(id)) {
            throw new RuntimeException("Train not found with ID: " + id);
        }

        trainRepository.deleteById(id);
    }
}