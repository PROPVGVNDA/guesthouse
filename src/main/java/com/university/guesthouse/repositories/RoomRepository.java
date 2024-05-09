package com.university.guesthouse.repositories;

import com.university.guesthouse.models.Guest;
import com.university.guesthouse.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByNumber(Integer number);
}
