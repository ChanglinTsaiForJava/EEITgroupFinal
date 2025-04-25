package eeit.OldProject.rita.Entity;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "user_points")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserPointsId")
    private Long userPointsId;

    @Column(name = "UserId")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ChangeType", nullable = true)
    private ChangeType changeType;

    @Column(name = "Points", nullable = true)
    private Integer points;

    @Column(name = "ReferenceId", nullable = true)
    private Integer referenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ReferenceType", nullable = true)
    private ReferenceType referenceType;

    @Column(name = "TotalPoints", nullable = true)
    private Integer totalPoints;

    @Column(name = "CreatedAt", nullable = true)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    private User user;
}

enum ChangeType {
    Earn,
    Redeem
}

enum ReferenceType {
    Appointment,
    Order
}

