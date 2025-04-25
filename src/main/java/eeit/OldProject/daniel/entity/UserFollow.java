package eeit.OldProject.daniel.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"follower","followed"})
@Entity
@Table(name = "user_follow", schema = "final")
public class UserFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserFollowId")
    private Long userFollowId;

    @Column(name = "IsFollowing")
    private Boolean isFollowing;

    @Column(name = "FollowAt")
    private LocalDateTime followAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FollowerId")
    @JsonIgnoreProperties("following")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FollowedId")
    @JsonIgnoreProperties("followers")
    private User followed;
}

