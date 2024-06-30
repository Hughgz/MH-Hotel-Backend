package com.example.MH_Hotel_Backend.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRoomDTO {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfClient;
    private String bookingConfirmationCode;
    private RoomDTO room;

    public BookingRoomDTO(Long id, LocalDate checkInDate, LocalDate checkOutDate,
                           String bookingConfirmationCode) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
