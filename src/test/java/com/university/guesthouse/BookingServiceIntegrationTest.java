package com.university.guesthouse;

import com.university.guesthouse.models.*;
import com.university.guesthouse.repositories.BookingRepository;
import com.university.guesthouse.services.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.*;

import java.time.Instant;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingServiceIntegrationTest {
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private GuestService guestService;

    @MockBean
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    private Guest guest;
    private Room room;
    private Booking booking;

    @Before
    public void setUp() {
        guest = new Guest("1", "Foo Bar", "fb@test.com", "At 12");
        room = new Room(1, Room.RoomType.STANDARD);
        booking = new Booking();
        booking.setStartDate(new Date());
        booking.setEndDate(new Date(System.currentTimeMillis() + 86400000));
    }

    @Test
    public void testCreateBookingSuccess() {
        when(guestService.findGuestByPhoneNumber("1")).thenReturn(guest);
        when(roomService.getRoomByNumber(1)).thenReturn(room);
        bookingService.createBooking(booking, "1", 1);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateBookingWithNonExistentGuest() {
        when(guestService.findGuestByPhoneNumber("1")).thenReturn(null);
        when(roomService.getRoomByNumber(1)).thenReturn(room);
        bookingService.createBooking(booking, "1", 1);
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateBookingWithNonExistentRoom() {
        when(guestService.findGuestByPhoneNumber("1")).thenReturn(guest);
        when(roomService.getRoomByNumber(1)).thenReturn(null);
        bookingService.createBooking(booking, "1", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateBookingWithInvalidDateRange() {
        when(guestService.findGuestByPhoneNumber("1")).thenReturn(guest);
        when(roomService.getRoomByNumber(1)).thenReturn(room);
        booking.setEndDate(booking.getStartDate());
        bookingService.createBooking(booking, "1", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateBookingWithEndDateBeforeStartDate() {
        when(guestService.findGuestByPhoneNumber("1")).thenReturn(guest);
        when(roomService.getRoomByNumber(1)).thenReturn(room);
        booking.setEndDate(new Date(booking.getStartDate().getTime() - 1000));
        bookingService.createBooking(booking, "1", 1);
    }
    @Test
    public void testIsRoomAvailable() {
        when(bookingRepository.findByRoomAndStatusIn(eq(room), anyList())).thenReturn(Arrays.asList(booking));
        boolean isAvailable = bookingService.isRoomAvailable(room, new Date(), new Date(System.currentTimeMillis() + 86400000));
        assertFalse(isAvailable);
        when(bookingRepository.findByRoomAndStatusIn(eq(room), anyList())).thenReturn(Arrays.asList());
        isAvailable = bookingService.isRoomAvailable(room, new Date(), new Date(System.currentTimeMillis() + 86400000));
        assertTrue(isAvailable);
    }

    @Test
    public void testListBookings() {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking));
        List<Booking> bookings = bookingService.listBookings();
        assertFalse(bookings.isEmpty());
        assertEquals(1, bookings.size());
    }

    @Test
    public void testGetBookingByID() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        Booking found = bookingService.getBookingByID(1L);
        assertNotNull(found);
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());
        found = bookingService.getBookingByID(1L);
        assertNull(found);
    }
    @Test
    public void testFindUnpaidBookingsCreatedBefore() {
        Booking oldBooking = new Booking();
        oldBooking.setCreationDate(Date.from(Instant.now().minusSeconds(86400 * 4)));
        oldBooking.setStatus(Booking.BookingStatus.CREATED);
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(oldBooking, booking));
        List<Booking> result = bookingService.findUnpaidBookingsCreatedBefore(LocalDateTime.now().minusDays(3));
        assertEquals(1, result.size());
        assertEquals(Booking.BookingStatus.CREATED, result.get(0).getStatus());
    }

    @Test
    public void testCancelUnpaidBookings() {
        Booking oldBooking = new Booking();
        oldBooking.setId(1L);
        oldBooking.setCreationDate(Date.from(Instant.now().minusSeconds(86400 * 4)));
        oldBooking.setStatus(Booking.BookingStatus.CREATED);
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(oldBooking));
        doAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            b.setStatus(Booking.BookingStatus.CANCELLED);
            return null;
        }).when(bookingRepository).save(any(Booking.class));
        bookingService.cancelUnpaidBookings();
        verify(bookingRepository, times(1)).save(oldBooking);
        assertEquals(Booking.BookingStatus.CANCELLED, oldBooking.getStatus());
    }

    @Test
    public void testFindPaidBookingsWithEndDateBefore() {
        Booking completedBooking = new Booking();
        completedBooking.setEndDate(Date.from(Instant.now().minusSeconds(86400)));
        completedBooking.setStatus(Booking.BookingStatus.PAID);
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(completedBooking, booking));
        List<Booking> result = bookingService.findPaidBookingsWithEndDateBefore(LocalDateTime.now());
        assertEquals(1, result.size());
        assertEquals(Booking.BookingStatus.PAID, result.get(0).getStatus());
    }

    @Test
    public void testCompletePaidBookings() {
        Booking paidBooking = new Booking();
        paidBooking.setId(1L);
        paidBooking.setEndDate(Date.from(Instant.now().minusSeconds(86400)));
        paidBooking.setStatus(Booking.BookingStatus.PAID);
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(paidBooking));
        doAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            b.setStatus(Booking.BookingStatus.COMPLETED);
            return null;
        }).when(bookingRepository).save(any(Booking.class));
        bookingService.completePaidBookings();
        verify(bookingRepository, times(1)).save(paidBooking);
        assertEquals(Booking.BookingStatus.COMPLETED, paidBooking.getStatus());
    }

    @Test
    public void testCalculateRoomPrice() {
        booking.setRoom(room);
        room.setType(Room.RoomType.DELUXE);
        double price = bookingService.calculateRoomPrice(booking);
        assertEquals(200.0, price, 0.01);
    }
}

