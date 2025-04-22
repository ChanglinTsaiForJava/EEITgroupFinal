package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductId")
    private Long productId;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "ProductDescription")
    private String productDescription;

    @Column(name = "ProductPrice")
    private BigDecimal productPrice;

    @Column(name = "Stock")
    private Integer stock;

    @Column(name = "ProductCategoryId")
    private Long productCategoryId;

    @Column(name = "CreatedAt")
    private Date createdAt;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "ProductCategoryId", insertable = false, updatable = false)
    private ProductCategory productCategory;
}

