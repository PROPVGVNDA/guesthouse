package com.university.guesthouse.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Guest {
    String phoneNumber;
    String fullName;
    String email;
    String address;
}
