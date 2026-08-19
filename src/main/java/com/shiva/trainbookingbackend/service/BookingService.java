package com.shiva.trainbookingbackend.service;

import com.shiva.trainbookingbackend.dto.BookingResponse;
import com.shiva.trainbookingbackend.dto.UserResponse;
import com.shiva.trainbookingbackend.entity.Booking;
import com.shiva.trainbookingbackend.entity.Train;
import com.shiva.trainbookingbackend.entity.User;
import com.shiva.trainbookingbackend.repository.BookingRepository;
import com.shiva.trainbookingbackend.repository.TrainRepository;
import com.shiva.trainbookingbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TrainRepository trainRepository;
    private final UserRepository userRepository;

    public BookingService(
            BookingRepository bookingRepository,
            TrainRepository trainRepository,
            UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.trainRepository = trainRepository;
        this.userRepository = userRepository;
    }

    public BookingResponse createBooking(
            String email,
            Long trainId,
            String passengerName,
            int passengerAge
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        if (train.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        int seatNumber =
                train.getTotalSeats() - train.getAvailableSeats() + 1;

        train.setAvailableSeats(train.getAvailableSeats() - 1);
        trainRepository.save(train);

        Booking booking = Booking.builder()
                .user(user)
                .train(train)
                .passengerName(passengerName)
                .passengerAge(passengerAge)
                .seatNumber(seatNumber)
                .bookingDate(LocalDateTime.now())
                .status("CONFIRMED")
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        return convertToResponse(savedBooking);
    }

    public List<BookingResponse> getUserBookings(Long userId) {

        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public BookingResponse getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return convertToResponse(booking);
    }

    public BookingResponse cancelBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if ("CANCELLED".equals(booking.getStatus())) {
            throw new RuntimeException("Booking is already cancelled");
        }

        Train train = booking.getTrain();

        train.setAvailableSeats(train.getAvailableSeats() + 1);
        trainRepository.save(train);

        booking.setStatus("CANCELLED");

        Booking savedBooking = bookingRepository.save(booking);

        return convertToResponse(savedBooking);
    }

    private BookingResponse convertToResponse(Booking booking) {

        User user = booking.getUser();
        Train train = booking.getTrain();

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .role(user.getRole())
                .build();

        return BookingResponse.builder()
                .id(booking.getId())
                .user(userResponse)
                .trainId(train.getId())
                .trainNumber(train.getTrainNumber())
                .trainName(train.getTrainName())
                .source(train.getSource())
                .destination(train.getDestination())
                .passengerName(booking.getPassengerName())
                .passengerAge(booking.getPassengerAge())
                .seatNumber(booking.getSeatNumber())
                .bookingDate(booking.getBookingDate())
                .status(booking.getStatus())
                .build();
    }
}