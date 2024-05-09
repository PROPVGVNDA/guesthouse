package com.university.guesthouse.services;

import com.university.guesthouse.models.Room;
import com.university.guesthouse.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    private Integer Number = 1;

    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    public void addRoom(Room room) {
        room.setNumber(Number++);
        roomRepository.save(room);
    }

    public void deleteRoom(Integer number) {
        Room room = roomRepository.findByNumber(number);
        if (room != null) {
            roomRepository.delete(room);
        }
    }

    public Room getRoomByNumber(Integer number) {
        return roomRepository.findByNumber(number);
    }
}
