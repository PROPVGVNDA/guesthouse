package com.university.guesthouse.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payment {
    private Long id;
    private Booking booking;
    private PaymentType paymentType;
    public enum PaymentType {
        CASH, CARD
    }
}
