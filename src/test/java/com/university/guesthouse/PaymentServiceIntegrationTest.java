package com.university.guesthouse;

import com.university.guesthouse.models.*;
import com.university.guesthouse.repositories.PaymentRepository;
import com.university.guesthouse.services.BookingService;
import com.university.guesthouse.services.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceIntegrationTest {
    @MockBean
    private BookingService bookingService;
    @Autowired
    private PaymentService paymentService;
    @MockBean
    private PaymentRepository paymentRepository;
    @Test(expected = IllegalStateException.class)
    public void testCreatePaymentFail() {
        Payment payment = new Payment();
        when(bookingService.getBookingByID(any())).thenReturn(null);
        paymentService.createPayment(payment, 1L);
    }

    @Test
    public void testCreatePayment_Success() {
        // Arrange
        Long bookingId = 1L;
        Booking booking = new Booking();
        Payment payment = new Payment();

        when(bookingService.getBookingByID(bookingId)).thenReturn(booking);

        // Act
        paymentService.createPayment(payment, bookingId);

        // Assert
        verify(bookingService).saveBooking(booking);
        verify(paymentRepository).save(payment);
        assertEquals(Booking.BookingStatus.PAID, booking.getStatus());
    }
}
