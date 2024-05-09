package com.university.guesthouse.repositories;

import com.university.guesthouse.models.Booking;
import com.university.guesthouse.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomAndStatusIn(Room room, List<Booking.BookingStatus> statuses);
}
