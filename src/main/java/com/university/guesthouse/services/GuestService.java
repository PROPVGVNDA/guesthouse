package com.university.guesthouse.services;

import com.university.guesthouse.models.Guest;
import com.university.guesthouse.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;

    public void addGuest(Guest guest) {
        guestRepository.save(guest);
    }

    public Guest findGuestByPhoneNumber(String phoneNumber) {
        return guestRepository.findByPhoneNumber(phoneNumber);
    }

    public void deleteGuest(String phoneNumber) {
        Guest guest = guestRepository.findByPhoneNumber(phoneNumber);
        if (guest != null) {
            guestRepository.delete(guest);
        }
    }

    public List<Guest> listGuests() {
        return guestRepository.findAll();
    }
}

