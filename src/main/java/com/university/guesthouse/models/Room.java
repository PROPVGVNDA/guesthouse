package com.university.guesthouse.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @Column(name = "room_number", nullable = false, unique = true)
    private Integer number;
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType type;
    public enum RoomType {
        STANDARD, DELUXE, SUITE
    }
}
