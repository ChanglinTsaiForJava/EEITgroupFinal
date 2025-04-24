package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemId")
    private Long orderItemId;

    @Column(name = "OrderId")
    private Long orderId;

    @Column(name = "ProductId")
    private Long productId;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "PriceAtOrder")
    private BigDecimal priceAtOrder;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "OrderId", insertable = false, updatable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "ProductId", insertable = false, updatable = false)
    private Product product;
}

