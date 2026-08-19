package com.shiva.trainbookingbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;

    private UserResponse user;

    private Long trainId;
    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;

    private String passengerName;
    private int passengerAge;
    private int seatNumber;

    private LocalDateTime bookingDate;
    private String status;
}