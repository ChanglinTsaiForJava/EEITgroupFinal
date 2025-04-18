package eeit.OldProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userAccount;

    private String userPassword;

    private String userName;

    private String emailAddress;

    private String phoneNumber;

    private String address;

    private String accountRank;

    private String category;

    private LocalDateTime createdAt;

    private String socialPlatformId;

    private String profileId;

    private String profilePicture;

    @Column(length = 1000)
    private String bio;

    @Column(length = 1000)
    private String intro;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients = new ArrayList<>();
}
