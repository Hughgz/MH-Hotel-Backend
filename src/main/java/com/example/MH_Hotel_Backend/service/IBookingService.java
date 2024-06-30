package com.example.MH_Hotel_Backend.service;

import com.example.MH_Hotel_Backend.model.BookingRoom;

import java.util.List;

public interface IBookingService {
    void cancelBooking(int bookingId);
    List<BookingRoom> getAllBookingsByRoomId(int roomId);
    String saveBooking(int roomId, BookingRoom bookingRequest);
    BookingRoom findByBookingConfirmationCode(String confirmationCode);
    List<BookingRoom> getAllBookings();
    List<BookingRoom> getBookingsByUserEmail(String email);
}
