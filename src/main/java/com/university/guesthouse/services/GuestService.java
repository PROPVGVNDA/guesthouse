package com.university.guesthouse.services;

import com.university.guesthouse.models.Guest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GuestService {

    private final List<Guest> guests = new ArrayList<>();

    public void addGuest(Guest guest) {
        guests.add(guest);
    }

    public Guest findGuestByPhoneNumber(String phoneNumber) {
        for (Guest guest : guests) {
            if (guest.getPhoneNumber().equals(phoneNumber)) {
                return guest;
            }
        }
        return null;
    }
    public void deleteGuest(String phoneNumber) {
        guests.removeIf(guest -> guest.getPhoneNumber().equals(phoneNumber));
    }

    public List<Guest> listGuests() {
        return guests;
    }
}

