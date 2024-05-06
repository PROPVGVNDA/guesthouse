package com.university.guesthouse.services;

import com.university.guesthouse.models.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final List<Room> rooms = new ArrayList<>();
    private Integer Number = 1;

    public List<Room> listRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        room.setNumber(Number++);
        rooms.add(room);
    }

    public void deleteRoom(Integer number) {
        rooms.removeIf(room -> room.getNumber().equals(number));
    }

    public Room getRoomByNumber(Integer number) {
        for (Room room : rooms) {
            if (room.getNumber().equals(number)) return room;
        }
        return null;
    }
}
