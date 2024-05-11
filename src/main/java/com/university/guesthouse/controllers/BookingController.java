package com.university.guesthouse.controllers;

import com.university.guesthouse.models.*;
import com.university.guesthouse.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "List all bookings", responses = {
            @ApiResponse(description = "Display bookings", responseCode = "200", content = @Content(mediaType = "text/html"))
    })
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingService.listBookings());
        return "bookings";
    }

    @GetMapping("/bookings/create")
    @Operation(summary = "Show form to create a new booking", responses = {
            @ApiResponse(description = "Booking creation form", responseCode = "200", content = @Content(mediaType = "text/html"))
    })
    public String showCreateBookingForm(Model model) {
        List<Guest> guests = guestService.listGuests();
        List<Room> rooms = roomService.listRooms();
        model.addAttribute("guests", guests);
        model.addAttribute("rooms", rooms);
        model.addAttribute("booking", new Booking());
        return "create-booking";
    }

    @GetMapping("/bookings/{id}")
    @Operation(summary = "Get information about a specific booking", responses = {
            @ApiResponse(description = "Booking details", responseCode = "200", content = @Content(mediaType = "text/html")),
            @ApiResponse(description = "Booking not found, redirect to bookings list", responseCode = "302", content = @Content(mediaType = "text/html"))
    })
    public String bookingInfo(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingByID(id);
        if (booking != null) {
            model.addAttribute("booking", booking);
            return "booking-info";
        }
        return "redirect:/bookings";
    }

    @PostMapping("/bookings/create")
    @Operation(summary = "Create a new booking", responses = {
            @ApiResponse(description = "Booking created, redirect to bookings list", responseCode = "302", content = @Content(mediaType = "text/html")),
            @ApiResponse(description = "Error during booking creation, redirect to error page", responseCode = "302", content = @Content(mediaType = "text/html"))
    })
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