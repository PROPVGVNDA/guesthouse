package com.university.guesthouse.services;

import com.university.guesthouse.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
        booking.setPrice(100);
        booking.setGuest(guest);
        booking.setRoom(room);
        booking.setStatus(Booking.BookingStatus.CREATED);
        booking.setId(counter.incrementAndGet());
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
}
