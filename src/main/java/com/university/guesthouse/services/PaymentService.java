package com.university.guesthouse.services;

import com.university.guesthouse.models.*;
import com.university.guesthouse.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    private final BookingService bookingService;

    public PaymentService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void createPayment(Payment payment, Long bookingID) {
        Booking booking = bookingService.getBookingByID(bookingID);
        if (booking == null) {
            throw new IllegalStateException("Booking with supplied ID not found");
        }
        payment.setBooking(booking);
        booking.setStatus(Booking.BookingStatus.PAID);
        bookingService.saveBooking(booking);
        paymentRepository.save(payment);
    }

    public List<Payment> listPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentByID(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
}
