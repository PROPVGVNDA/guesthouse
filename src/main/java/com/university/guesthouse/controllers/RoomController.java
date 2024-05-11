package com.university.guesthouse.controllers;


import com.university.guesthouse.services.RoomService;
import com.university.guesthouse.models.Room;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/rooms")
    @Operation(summary = "List all rooms", responses = {
            @ApiResponse(description = "Display rooms", responseCode = "200", content = @Content(mediaType = "text/html"))
    })
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.listRooms());
        return "rooms";
    }
    @GetMapping("/rooms/{number}")
    @Operation(summary = "Get information about a specific room", responses = {
            @ApiResponse(description = "Room details", responseCode = "200", content = @Content(mediaType = "text/html")),
            @ApiResponse(description = "Room not found, redirect to rooms list", responseCode = "302", content = @Content(mediaType = "text/html"))
    })
    public String roomInfo(@PathVariable Integer number, Model model) {
        Room room = roomService.getRoomByNumber(number);
        if (room != null) {
            model.addAttribute("room", room);
            return "room-info";
        }
        return "redirect:/rooms";
    }
    @PostMapping("/rooms/create")
    @Operation(summary = "Create a new room", responses = {
            @ApiResponse(description = "Room created, redirect to rooms list", responseCode = "302", content = @Content(mediaType = "text/html"))
    })
    public String createRoom(Room room) {
        roomService.addRoom(room);
        return "redirect:/rooms";
    }
}
