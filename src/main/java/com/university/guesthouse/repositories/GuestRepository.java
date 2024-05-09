package com.university.guesthouse.repositories;

import com.university.guesthouse.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Guest findByPhoneNumber(String phoneNumber);
}
