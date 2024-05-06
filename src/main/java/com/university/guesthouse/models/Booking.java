package com.university.guesthouse.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class Booking {
    private Long id;
    private Guest guest;
    private Room room;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private BookingStatus status;
    private double price;

    public enum BookingStatus {
        CREATED, PAID, CANCELLED, COMPLETED
    }
}