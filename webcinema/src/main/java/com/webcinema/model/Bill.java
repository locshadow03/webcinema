package com.webcinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bill_id;

    private String bill_code;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Ticket> tickets;

    private int totalAmount;

    private final String paymentMethod = "Thanh toán khi nhận vé!";

    private String paymentStatus;

    @Column(nullable = false, updatable = false)
    private LocalDate bookDate;

    @PrePersist
    protected void onCreate() {
        bookDate = LocalDate.now();
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
