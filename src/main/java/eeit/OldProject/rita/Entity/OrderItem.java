package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemId")
    private Long orderItemId;

    @Column(name = "OrderId")
    private Long orderId;

    // 通用欄位：商品 or 課程
    @Column(name = "ItemType", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name = "ItemId", nullable = false)
    private Long itemId;

    @Column(name = "Quantity", nullable = true)
    private Integer quantity;

    @Column(name = "PriceAtOrder", precision = 10, scale = 2, nullable = true)
    private BigDecimal priceAtOrder;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "OrderId", insertable = false, updatable = false)
    private Orders order;

    public enum ItemType {
        PRODUCT,
        COURSE
    }
}


