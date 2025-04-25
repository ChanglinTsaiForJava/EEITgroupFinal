package eeit.OldProject.rita.Entity;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Long orderId;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "OrderDate", nullable = true)
    private Date orderDate;

    @Column(name = "TotalAmount", precision = 10, scale = 2, nullable = true)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = true)
    private OrderStatus status;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    private User user;
}

enum OrderStatus {
    Pending,
    Paid,
    Shipped,
    Delivered,
    Cancelled
}

