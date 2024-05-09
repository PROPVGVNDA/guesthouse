package com.university.guesthouse;

import com.university.guesthouse.controllers.GuestController;
import com.university.guesthouse.models.Guest;
import com.university.guesthouse.services.GuestService;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GuestController.class)
public class GuestRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuestService guestService;

    private List<Guest> guests;

    @Before
    public void setup() {
        guests = new ArrayList<>();
        guests.add(new Guest("1234567890", "John Doe", "john.doe@example.com", "123 Elm Street"));
        guests.add(new Guest("0987654321", "Jane Smith", "jane.smith@example.com", "456 Oak Avenue"));
    }

    @Test
    public void testGetGuests() throws Exception {
        when(guestService.listGuests()).thenReturn(guests);
        mockMvc.perform(get("/guests"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("guests"))
                .andExpect(model().attribute("guests", guests))
                .andExpect(view().name("guests"));
    }

    @Test
    public void testCreateGuest() throws Exception {
        Guest guest = guests.getFirst();
        mockMvc.perform(post("/guests")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("phoneNumber", guest.getPhoneNumber())
                        .param("fullName", guest.getFullName())
                        .param("emailAddress", guest.getEmail())
                        .param("address", guest.getAddress()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/guests"));

        verify(guestService).addGuest(any(Guest.class));
    }
}
