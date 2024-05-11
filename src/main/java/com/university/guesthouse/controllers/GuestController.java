package com.university.guesthouse.controllers;


import com.university.guesthouse.models.Guest;
import com.university.guesthouse.services.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "List all guests", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "text/html"))
    })
    public String listGuests(Model model) {
        model.addAttribute("guests", guestService.listGuests());
        return "guests";
    }

    @PostMapping("/guests")
    @Operation(summary = "Create a new guest", responses = {
            @ApiResponse(description = "Redirect to guests list", responseCode = "302", content = @Content(mediaType = "text/html"))
    })
    public String createGuest(Guest guest) {
        guestService.addGuest(guest);
        return "redirect:/guests";
    }
}
