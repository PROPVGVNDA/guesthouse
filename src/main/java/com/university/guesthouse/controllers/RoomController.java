package com.university.guesthouse.controllers;


import com.university.guesthouse.services.RoomService;
import com.university.guesthouse.models.Room;
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
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.listRooms());
        return "rooms";
    }
    @GetMapping("/rooms/{number}")
    public String roomInfo(@PathVariable Integer number, Model model) {
        Room room = roomService.getRoomByNumber(number);
        if (room != null) {
            model.addAttribute("room", room);
            return "room-info";
        }
        return "redirect:/rooms";
    }
    @PostMapping("/rooms/create")
    public String createRoom(Room room) {
        roomService.addRoom(room);
        return "redirect:/rooms";
    }
    @PostMapping("/rooms/delete/{number}")
    public String deleteRoom(@PathVariable Integer number) {
        roomService.deleteRoom(number);
        return "redirect:/rooms";
    }
}
