package com.university.guesthouse.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest {
    @Id
    @Column(name = "phone_number", unique = true, nullable = false)
    String phoneNumber;
    @Column(name = "full_name")
    String fullName;
    @Column(name = "email_address", unique = true)
    String email;
    @Column(name = "address")
    String address;
}
