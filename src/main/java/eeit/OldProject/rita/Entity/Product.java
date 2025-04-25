package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductId")
    private Long productId;

    @Column(name = "ProductName", length = 100, nullable = false)
    private String productName;

    @Column(name = "ProductDescription", columnDefinition = "TEXT", nullable = true)
    private String productDescription;

    @Column(name = "ProductPrice", precision = 10, scale = 2, nullable = true)
    private BigDecimal productPrice;

    @Column(name = "Stock", nullable = true)
    private Integer stock;

    @Column(name = "ProductCategoryId")
    private Long productCategoryId;

    @Column(name = "CreatedAt", nullable = true)
    private Date createdAt;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "ProductCategoryId", insertable = false, updatable = false)
    private ProductCategory productCategory;
}

