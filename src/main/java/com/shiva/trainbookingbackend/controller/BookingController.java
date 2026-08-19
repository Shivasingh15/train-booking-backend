package com.shiva.trainbookingbackend.controller;

import com.shiva.trainbookingbackend.dto.BookingResponse;
import com.shiva.trainbookingbackend.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestParam Long trainId,
            @RequestParam String passengerName,
            @RequestParam int passengerAge,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                bookingService.createBooking(
                        email,
                        trainId,
                        passengerName,
                        passengerAge
                )
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getUserBookings(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                bookingService.getUserBookings(userId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                bookingService.cancelBooking(id)
        );
    }
}