package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private Long categoryId;

    @Column(name = "CategoryName", length = 50, nullable = false)
    private String categoryName;
}

