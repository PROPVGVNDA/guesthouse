package com.university.guesthouse;

import com.university.guesthouse.controllers.RoomController;
import com.university.guesthouse.models.Room;
import com.university.guesthouse.services.RoomService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomController.class)
public class RoomRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Test
    public void testRooms() throws Exception {
        List<Room> rooms = Arrays.asList(new Room(1, Room.RoomType.STANDARD));
        when(roomService.listRooms()).thenReturn(rooms);
        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rooms"))
                .andExpect(view().name("rooms"));
    }

    @Test
    public void testRoomInfo() throws Exception {
        Room room = new Room(1, Room.RoomType.STANDARD);
        when(roomService.getRoomByNumber(1)).thenReturn(room);
        mockMvc.perform(get("/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("room"))
                .andExpect(view().name("room-info"));
    }

    @Test
    public void testRoomNullInfo() throws Exception {
        when(roomService.getRoomByNumber(1)).thenReturn(null);
        mockMvc.perform(get("/rooms/1"))
                .andExpect(redirectedUrl("/rooms"));
    }

    @Test
    public void testCreateRoom() throws Exception {
        mockMvc.perform(post("/rooms/create")
                        .param("number", "1")
                        .param("type", "STANDARD"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rooms"));
    }

    @Test
    public void testDeleteRoom() throws Exception {
        mockMvc.perform(post("/rooms/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rooms"));
    }

}
