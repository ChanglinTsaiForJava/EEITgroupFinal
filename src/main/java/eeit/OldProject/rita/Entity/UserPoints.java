package eeit.OldProject.rita.Entity;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.sql.Timestamp;

@Entity
@Table(name = "user_points")
@Getter
@Setter
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
    @Column(name = "ChangeType")
    private ChangeType changeType;

    @Column(name = "Points")
    private Integer points;

    @Column(name = "ReferenceId")
    private Integer referenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ReferenceType")
    private ReferenceType referenceType;

    @Column(name = "TotalPoints")
    private Integer totalPoints;

    @Column(name = "CreatedAt")
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

