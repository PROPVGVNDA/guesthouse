package com.university.guesthouse.controllers;


import com.university.guesthouse.models.Booking;
import com.university.guesthouse.models.Payment;
import com.university.guesthouse.services.BookingService;
import com.university.guesthouse.services.PaymentService;
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
public class PaymentController {
    private final PaymentService paymentService;
    private final BookingService bookingService;

    @GetMapping("/payments")
    @Operation(summary = "List all payments", responses = {
            @ApiResponse(description = "Display payments", responseCode = "200", content = @Content(mediaType = "text/html"))
    })
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.listPayments());
        return "payments";
    }

    @GetMapping("/payments/create")
    @Operation(summary = "Show form to create a new payment", responses = {
            @ApiResponse(description = "Payment creation form", responseCode = "200", content = @Content(mediaType = "text/html"))
    })
    public String showCreatePaymentForm(Model model) {
        List<Booking> bookings = bookingService.listBookings();
        model.addAttribute("bookings", bookings);
        model.addAttribute("payment", new Payment());
        return "create-payment";
    }

    @GetMapping("/payments/{id}")
    @Operation(summary = "Get information about a specific payment", responses = {
            @ApiResponse(description = "Payment details", responseCode = "200", content = @Content(mediaType = "text/html")),
            @ApiResponse(description = "Payment not found, redirect to payments list", responseCode = "302", content = @Content(mediaType = "text/html"))
    })
    public String paymentInfo(@PathVariable Long id, Model model) {
        Payment payment = paymentService.getPaymentByID(id);
        if (payment != null) {
            model.addAttribute("payment", payment);
            return "payment-info";
        }
        return "redirect:/payments";
    }

    @PostMapping("/payments/create")
    @Operation(summary = "Create a new payment", responses = {
            @ApiResponse(description = "Payment created, redirect to payments list", responseCode = "302", content = @Content(mediaType = "text/html")),
            @ApiResponse(description = "Error during payment creation, redirect to error page", responseCode = "302", content = @Content(mediaType = "text/html"))
    })
    public String createPayment(@ModelAttribute Payment payment, @RequestParam Long bookingID, RedirectAttributes redirectAttributes) {
        try {
            paymentService.createPayment(payment, bookingID);
            return "redirect:/payments";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }
}
