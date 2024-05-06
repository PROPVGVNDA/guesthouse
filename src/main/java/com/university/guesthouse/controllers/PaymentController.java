package com.university.guesthouse.controllers;


import com.university.guesthouse.models.Booking;
import com.university.guesthouse.models.Payment;
import com.university.guesthouse.services.BookingService;
import com.university.guesthouse.services.PaymentService;
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
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.listPayments());
        return "payments";
    }

    @GetMapping("/payments/create")
    public String showCreatePaymentForm(Model model) {
        List<Booking> bookings = bookingService.listBookings();
        model.addAttribute("bookings", bookings);
        model.addAttribute("payment", new Payment());
        return "create-payment";
    }

    @GetMapping("/payments/{id}")
    public String paymentInfo(@PathVariable Long id, Model model) {
        Payment payment = paymentService.getPaymentByID(id);
        if (payment != null) {
            model.addAttribute("payment", payment);
            return "payment-info";
        }
        return "redirect:/payments";
    }

    @PostMapping("/payments/create")
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
