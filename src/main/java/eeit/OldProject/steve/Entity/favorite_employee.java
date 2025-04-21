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
@Table(name = "favorite_employee")
public class favorite_employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavCaregiverId")
    private Long favCaregiverId;

    @Column(name = "ArchivedDate")
    private LocalDateTime archivedDate;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "CaregiverId")
    private Long caregiverId;
}
