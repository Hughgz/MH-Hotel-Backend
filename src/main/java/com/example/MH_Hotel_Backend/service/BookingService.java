package com.example.MH_Hotel_Backend.service;

import com.example.MH_Hotel_Backend.model.BookingRoom;
import com.example.MH_Hotel_Backend.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{
    private final BookingRepository repository;
    @Override
    public void cancelBooking(int bookingId) {
        repository.deleteById(bookingId);
    }

    @Override
    public List<BookingRoom> getAllBookingsByRoomId(int roomId) {
        return repository.findBookingRoomById(roomId);
    }

    @Override
    public String saveBooking(int roomId, BookingRoom bookingRequest) {
        if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){

        }
        return null;
    }

    @Override
    public BookingRoom findByBookingConfirmationCode(String confirmationCode) {
        return null;
    }

    @Override
    public List<BookingRoom> getAllBookings() {
        return null;
    }

    @Override
    public List<BookingRoom> getBookingsByUserEmail(String email) {
        return null;
    }
}
