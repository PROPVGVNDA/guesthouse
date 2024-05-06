package com.university.guesthouse.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Room {
    private Integer number;
    private RoomType type;
    public enum RoomType {
        STANDARD, DELUXE, SUITE
    }
}
