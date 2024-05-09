package com.university.guesthouse;

import com.university.guesthouse.controllers.GuestController;
import com.university.guesthouse.controllers.PaymentController;
import com.university.guesthouse.models.Booking;
import com.university.guesthouse.models.Guest;
import com.university.guesthouse.models.Payment;
import com.university.guesthouse.repositories.PaymentRepository;
import com.university.guesthouse.services.BookingService;
import com.university.guesthouse.services.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingService bookingService;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private PaymentRepository paymentRepository;
    private List<Payment> payments;
    private List<Booking> bookings;
    @Before
    public void setup() {
        payments = new ArrayList<>();
        bookings = new ArrayList<>();
        Booking booking = new Booking();
        booking.setId(1L);
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setBooking(booking);
        payments.add(payment);
        bookings.add(booking);
    }
    @Test
    public void testListPayments() throws Exception {
        when(paymentService.listPayments()).thenReturn(payments);
        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payments"))
                .andExpect(model().attribute("payments", payments))
                .andExpect(view().name("payments"));
    }

    @Test
    public void testShowCreatePaymentForm() throws Exception {
        when(bookingService.listBookings()).thenReturn(bookings);
        mockMvc.perform(get("/payments/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("payment"))
                .andExpect(model().attributeExists("bookings"))
                .andExpect(model().attribute("bookings", bookings))
                .andExpect(view().name("create-payment"));
    }

    @Test
    public void testGetNullPaymentInfo() throws Exception {
        when(paymentService.getPaymentByID(any())).thenReturn(null);
        mockMvc.perform(get("/payments/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payments"));
    }

    @Test
    public void testGetPaymentInfo() throws Exception {
        when(paymentService.getPaymentByID(any())).thenReturn(payments.getFirst());
        mockMvc.perform(get("/payments/1"))
                .andExpect(model().attributeExists("payment"))
                .andExpect(model().attribute("payment", payments.getFirst()))
                .andExpect(view().name("payment-info"));
    }

    @Test
    public void testCreatePayment() throws Exception {
        when(bookingService.getBookingByID(any())).thenReturn(new Booking());
        mockMvc.perform(post("/payments/create")
                        .param("bookingID", "20")
                        .flashAttr("payment", new Payment()))
                .andExpect(redirectedUrl("/payments"));
        verify(paymentService).createPayment(any(Payment.class), eq(20L));
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
