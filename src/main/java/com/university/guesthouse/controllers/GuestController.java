package com.university.guesthouse.controllers;


import com.university.guesthouse.models.Guest;
import com.university.guesthouse.services.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@RequiredArgsConstructor
public class GuestController {
    private final GuestService guestService;

    @GetMapping("/guests")
    public String listGuests(Model model) {
        model.addAttribute("guests", guestService.listGuests());
        return "guests";
    }

    @PostMapping("/guests")
    public String createGuest(Guest guest) {
        guestService.addGuest(guest);
        return "redirect:/guests";
    }
}
