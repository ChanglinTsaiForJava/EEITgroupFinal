package eeit.OldProject.steve.Entity;

import eeit.OldProject.yuuhou.Entity.Caregiver;
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
public class Favorite_employee {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CaregiverId", insertable = false, updatable = false)
    private Caregiver caregiver;
}
