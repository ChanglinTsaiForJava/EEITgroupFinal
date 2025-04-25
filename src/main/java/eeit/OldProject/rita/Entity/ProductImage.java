package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImageId")
    private Long imageId;

    @Column(name = "ProductId")
    private Long productId;

    @Lob
    @Column(name = "ProductImage", columnDefinition = "LONGBLOB", nullable = true)
    private byte[] productImage;

    @Column(name = "IsPrimary", nullable = true)
    private Boolean isPrimary;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "ProductId", insertable = false, updatable = false)
    private Product product;
}

