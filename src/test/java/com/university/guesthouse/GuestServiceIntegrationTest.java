package com.university.guesthouse;

import com.university.guesthouse.models.Guest;
import com.university.guesthouse.repositories.GuestRepository;
import com.university.guesthouse.services.GuestService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GuestServiceIntegrationTest {
    @MockBean
    private GuestRepository guestRepository;

    @Autowired
    private GuestService guestService;

    @Test
    public void testAddGuest() {
        Guest guest = new Guest("1234567890", "John Doe", "john.doe@example.com", "123 Elm Street");
        guestService.addGuest(guest);
        verify(guestRepository).save(any(Guest.class));
    }

    @Test
    public void testFindGuestByPhoneNumber() {
        Guest guest = new Guest("1234567890", "John Doe", "john.doe@example.com", "123 Elm Street");
        when(guestRepository.findByPhoneNumber("1234567890")).thenReturn(guest);
        Guest found = guestService.findGuestByPhoneNumber("1234567890");
        assertThat(found).isEqualTo(guest);
    }

    @Test
    public void testDeleteGuest() {
        Guest guest = new Guest("1234567890", "John Doe", "john.doe@example.com", "123 Elm Street");
        when(guestRepository.findByPhoneNumber("1234567890")).thenReturn(guest);
        guestService.deleteGuest("1234567890");
        verify(guestRepository).delete(guest);
    }

    @Test
    public void testDeleteNullGuest() {
        when(guestRepository.findByPhoneNumber("1234567890")).thenReturn(null);
        guestService.deleteGuest("1234567890");
        verify(guestRepository, never()).delete(any());
    }

    @Test
    public void testListGuests() {
        List<Guest> guests = Collections.singletonList(new Guest("1234567890", "John Doe", "john.doe@example.com", "123 Elm Street"));
        when(guestRepository.findAll()).thenReturn(guests);
        List<Guest> resultList = guestService.listGuests();
        assertThat(resultList).isEqualTo(guests);
    }
}
