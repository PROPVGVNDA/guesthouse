package com.university.guesthouse.controllers;

import com.university.guesthouse.models.*;
import com.university.guesthouse.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final RoomService roomService;
    private final GuestService guestService;

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingService.listBookings());
        return "bookings";
    }

    @GetMapping("/bookings/create")
    public String showCreateBookingForm(Model model) {
        List<Guest> guests = guestService.listGuests();  // Assuming a method that lists all guests
        List<Room> rooms = roomService.listRooms();     // Assuming a method that lists all rooms
        model.addAttribute("guests", guests);
        model.addAttribute("rooms", rooms);
        model.addAttribute("booking", new Booking());
        return "create-booking";
    }
    @GetMapping("/bookings/{id}")
    public String bookingInfo(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingByID(id);
        System.out.println(booking.toString());
        if (booking != null) {
            model.addAttribute("booking", booking);
            return "booking-info";
        }
        return "redirect:/bookings";
    }

    @PostMapping("/bookings/create")
    public String createBooking(@ModelAttribute Booking booking, @RequestParam String guestNumber, @RequestParam Integer roomId, RedirectAttributes redirectAttributes) {
        try {
            bookingService.createBooking(booking, guestNumber, roomId);
            return "redirect:/bookings";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }
}
