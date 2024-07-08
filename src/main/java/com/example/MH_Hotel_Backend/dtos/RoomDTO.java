package com.example.MH_Hotel_Backend.dtos;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Data
public class RoomDTO {
    private int id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;
    private String photo;
    private List<BookingRoomDTO> bookings;

    public RoomDTO(int id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomDTO(int id, String roomType, BigDecimal roomPrice, boolean isBooked,
                        byte[] photoBytes) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.getEncoder().encodeToString(photoBytes) : null;
//        this.bookings = bookings;
    }
}
