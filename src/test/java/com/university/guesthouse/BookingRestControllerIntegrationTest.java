package com.university.guesthouse;

import com.university.guesthouse.controllers.BookingController;
import com.university.guesthouse.models.Booking;
import com.university.guesthouse.models.Guest;
import com.university.guesthouse.models.Room;
import com.university.guesthouse.services.BookingService;
import com.university.guesthouse.services.GuestService;
import com.university.guesthouse.services.RoomService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingController.class)
public class BookingRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingService bookingService;
    @MockBean
    private RoomService roomService;
    @MockBean
    private GuestService guestService;

    @Test
    public void testBookings() throws Exception {
        Booking booking = new Booking();
        booking.setId(1L);
        List<Booking> bookings = Arrays.asList(booking);
        when(bookingService.listBookings()).thenReturn(bookings);
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bookings"))
                .andExpect(view().name("bookings"));
    }

    @Test
    public void testBookingShowCreate() throws Exception {
        Room room = new Room(1, Room.RoomType.STANDARD);
        List<Room> rooms = Arrays.asList(room);
        Guest guest = new Guest("1", "Foo Bar", "fb@test.com", "At 12");
        List<Guest> guests = Arrays.asList(guest);
        when(roomService.listRooms()).thenReturn(rooms);
        when(guestService.listGuests()).thenReturn(guests);

        Booking booking = new Booking();
        booking.setId(1L);
        List<Booking> bookings = Arrays.asList(booking);
        mockMvc.perform(get("/bookings/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("guests"))
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attributeExists("booking"))
                .andExpect(view().name("create-booking"));
    }

    @Test
    public void testBookingInfo() throws Exception {
        Room room = new Room(1, Room.RoomType.STANDARD);
        Guest guest = new Guest("1", "Foo Bar", "fb@test.com", "At 12");

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setRoom(room);
        booking.setGuest(guest);
        booking.setStartDate(new Date());
        booking.setEndDate(new Date(System.currentTimeMillis() + 86400000));
        booking.setStatus(Booking.BookingStatus.CREATED);
        booking.setCreationDate(new Date());
        when(bookingService.getBookingByID(1L)).thenReturn(booking);

        mockMvc.perform(get("/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("booking-info"))
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attribute("booking", booking));
    }

    @Test
    public void testNullBookingInfo() throws Exception {
        mockMvc.perform(get("/bookings/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bookings"));
    }

    @Test
    public void createBookingSuccess() throws Exception {
        Booking booking = new Booking();
        booking.setId(1L);

        mockMvc.perform(post("/bookings/create")
                        .param("guestNumber", "1234567890")
                        .param("roomId", "1")
                        .flashAttr("booking", booking))
                .andExpect(redirectedUrl("/bookings"));

        verify(bookingService).createBooking(any(Booking.class), eq("1234567890"), eq(1));
    }
}
