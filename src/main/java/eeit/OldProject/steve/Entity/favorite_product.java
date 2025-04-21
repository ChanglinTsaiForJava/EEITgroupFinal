package eeit.OldProject.steve.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorite_product")
public class favorite_product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavProductId")
    private Long favProductId;

    @Column(name = "ArchivedDate")
    private LocalDateTime archivedDate;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "ProductId")
    private Long productId;
}
