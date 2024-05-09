package com.university.guesthouse.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "guest_phone_number", nullable = false)
    private Guest guest;
    @ManyToOne
    @JoinColumn(name = "room_number", nullable = false)
    private Room room;
    @Column(name = "start_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @Column(name = "creation_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;
    @Column(name = "price", nullable = false)
    private double price;

    public enum BookingStatus {
        CREATED, PAID, CANCELLED, COMPLETED
    }
}