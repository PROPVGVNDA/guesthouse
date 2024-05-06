package com.university.guesthouse.services;

import com.university.guesthouse.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PaymentService {
    private final List<Payment> payments = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    private final BookingService bookingService;

    public PaymentService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void createPayment(Payment payment, Long bookingID) {
        Booking booking = bookingService.getBookingByID(bookingID);
        if (booking == null) {
            throw new IllegalStateException("Booking with supplied ID not found");
        }
        payment.setPaymentType(Payment.PaymentType.CARD);
        payment.setBooking(booking);
        booking.setStatus(Booking.BookingStatus.PAID);
        payment.setId(counter.incrementAndGet());
        payments.add(payment);
    }

    public List<Payment> listPayments() {
        return payments;
    }

    public Payment getPaymentByID(Long id) {
        for (Payment payment : payments) {
            if (payment.getId().equals(id)) {
                return payment;
            }
        }
        return null;
    }
}
