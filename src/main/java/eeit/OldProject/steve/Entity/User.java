package eeit.OldProject.steve.Entity;

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
    @Column(name = "UserId")
    private Long userId;

    @Column(name = "UserAccount")
    private String userAccount;

    @Column(name = "UserPassword")
    private String userPassword;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "EmailAddress")
    private String emailAddress;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "Address")
    private String address;

    @Column(name = "AccountRank")
    private String accountRank;

    @Column(name = "Category")
    private String category;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "SocialPlatformId")
    private String socialPlatformId;


    @Column(name = "ProfileId")
    private Long profileId;

    @Column(name = "ProfilePicture")
    private String profilePicture;

    @Column(length = 1000, name = "Bio")
    private String bio;

    @Column(length = 1000, name = "Intro")
    private String intro;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients = new ArrayList<>();
}
