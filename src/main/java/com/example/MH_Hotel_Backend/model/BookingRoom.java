package com.example.MH_Hotel_Backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class BookingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "check_in")
    private LocalDate checkInDate;
    @Column(name = "check_out")
    private LocalDate checkOutDate;
    @Column(name = "client_fullName")
    private String clientFullName;
    @Column(name = "client_email")
    private String clientEmail;
    @Column(name = "client_phone")
    private String clientPhone;
    @Column(name = "adults")
    private int numOfAdults;
    @Column(name = "children")
    private int numOfChildren;
    @Column(name = "total_client")
    private int totalNumOfClient;
    @Column(name = "booking_code")
    private String bookingConfirmationCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public void calculateTotalNumOfClient() { //tính tổng số lượng khách đặt phòng
        this.totalNumOfClient = this.numOfAdults + this.numOfChildren;
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateTotalNumOfClient();
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateTotalNumOfClient();
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
