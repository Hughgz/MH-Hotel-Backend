package com.example.MH_Hotel_Backend.repository;

import com.example.MH_Hotel_Backend.model.BookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookingRoom, Integer> {
    List<BookingRoom> findBookingRoomById(int roomId);
    Optional<BookingRoom> findBookingRoomByBookingConfirmationCode(String confirmationCode);
    List<BookingRoom> findByClientEmail(String email);
}
