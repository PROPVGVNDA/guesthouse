package com.university.guesthouse;

import com.university.guesthouse.models.Room;
import com.university.guesthouse.repositories.RoomRepository;
import com.university.guesthouse.services.RoomService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceIntegrationTest {
    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Test
    public void testAddRoom() {
        Room room = new Room();
        room.setType(Room.RoomType.STANDARD);
        roomService.addRoom(room);
        verify(roomRepository).save(room);
    }

    @Test
    public void testListRooms() {
        Room room = new Room(1, Room.RoomType.STANDARD);
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room));
        List<Room> rooms = roomService.listRooms();
        assertThat(rooms).containsExactly(room);
    }

    @Test
    public void testDeleteRoom() {
        Room room = new Room(1, Room.RoomType.STANDARD);
        when(roomRepository.findByNumber(1)).thenReturn(room);
        roomService.deleteRoom(1);
        verify(roomRepository).delete(room);
    }

    @Test
    public void testDeleteNullRoom() {
        when(roomRepository.findByNumber(1)).thenReturn(null);
        roomService.deleteRoom(1);
        verify(roomRepository, never()).delete(any());
    }

    @Test
    public void testGetRoomByNumber() {
        Room room = new Room(1, Room.RoomType.STANDARD);
        when(roomRepository.findByNumber(1)).thenReturn(room);
        Room found = roomService.getRoomByNumber(1);
        assertThat(found).isEqualTo(room);
    }

}
