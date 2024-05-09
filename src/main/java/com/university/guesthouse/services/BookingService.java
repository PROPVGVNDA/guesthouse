package com.university.guesthouse.services;

import com.university.guesthouse.models.*;
import lombok.extern.flogger.Flogger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final List<Booking> bookings = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    private final RoomService roomService;
    private final GuestService guestService;

    public BookingService(RoomService roomService, GuestService guestService) {
        this.roomService = roomService;
        this.guestService = guestService;
    }
    public void createBooking(Booking booking, String phoneNumber, Integer roomNumber) {
        Guest guest = guestService.findGuestByPhoneNumber(phoneNumber);
        Room room = roomService.getRoomByNumber(roomNumber);
        if (guest == null || room == null) {
            throw new IllegalStateException("Guest or Room not found");
        }
        if (booking.getEndDate().before(booking.getStartDate()) || booking.getEndDate().equals(booking.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before or the same as start date");
        }
        if (!isRoomAvailable(room, booking.getStartDate(), booking.getEndDate())) {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }
        booking.setGuest(guest);
        booking.setRoom(room);
        booking.setStatus(Booking.BookingStatus.CREATED);
        booking.setId(counter.incrementAndGet());
        booking.setCreationDate(new Date());
        booking.setPrice(this.calculateRoomPrice(booking));
        bookings.add(booking);
    }

    public boolean isRoomAvailable(Room room, Date start, Date end) {
        for (Booking booking : bookings) {
            if (booking.getRoom().equals(room)) {
                // TODO: maybe make end date exclusive
                boolean overlaps = !(booking.getEndDate().before(start) || booking.getStartDate().after(end));
                if (overlaps && (booking.getStatus() == Booking.BookingStatus.CREATED || booking.getStatus() == Booking.BookingStatus.PAID)) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Booking> listBookings() {
        return bookings;
    }

    public Booking getBookingByID(Long id) {
        for (Booking booking : bookings) {
            if (booking.getId().equals(id)) return booking;
        }
        return null;
    }

    public List<Booking> findUnpaidBookingsCreatedBefore(LocalDateTime date) {
        return bookings.stream()
                .filter(booking -> booking.getStatus() == Booking.BookingStatus.CREATED)
                .filter(booking -> {
                    LocalDateTime creationLocalDateTime = booking.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return creationLocalDateTime.isBefore(date);
                })
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 * * * * *")
    public void cancelUnpaidBookings() {
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        List<Booking> unpaidBookings = this.findUnpaidBookingsCreatedBefore(threeDaysAgo);
        unpaidBookings.forEach(booking -> {
            booking.setStatus(Booking.BookingStatus.CANCELLED);
        });
    }

    public List<Booking> findPaidBookingsWithEndDateBefore(LocalDateTime date) {
        return bookings.stream()
                .filter(booking -> booking.getStatus() == Booking.BookingStatus.PAID)
                .filter(booking -> {
                    LocalDateTime endLocalDateTime = booking.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return endLocalDateTime.isBefore(date);
                })
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 * * * * *")
    public void completePaidBookings() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Booking> paidBookings = this.findPaidBookingsWithEndDateBefore(currentDateTime);
        paidBookings.forEach(booking -> {
            System.out.println("Completing booking with ID " + booking.getId());
            booking.setStatus(Booking.BookingStatus.COMPLETED);
        });
    }

    private double calculateRoomPrice(Booking booking) {
        long numberOfDays = ChronoUnit.DAYS.between(
                booking.getStartDate().toInstant(),
                booking.getEndDate().toInstant()
        );

        double basePrice = switch (booking.getRoom().getType()) {
            case STANDARD -> 100;
            case DELUXE -> 200;
            case SUITE -> 400;
            default -> 0;
        };
        return basePrice * numberOfDays;
    }
}
