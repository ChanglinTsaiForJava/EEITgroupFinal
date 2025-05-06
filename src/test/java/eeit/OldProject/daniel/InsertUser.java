//package eeit.OldProject.daniel;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import eeit.OldProject.steve.Entity.User;
//import eeit.OldProject.steve.Repository.UserRepository;
//
//@SpringBootTest
//public class InsertUser {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void testSaveAndPrintFiveUsers() {
//        // Prepare five User instances without using @Builder
//        List<User> users = new ArrayList<>();
//
//        User u1 = new User();
//        u1.setUserAccount("account1");
//        u1.setUserPassword("pass1");
//        u1.setUserName("User One");
//        u1.setEmailAddress("one@example.com");
//        u1.setPhoneNumber("0911000001");
//        u1.setAddress("Address 1");
//        u1.setAccountRank("1");
//        u1.setCategory("Category A");
//        u1.setCreatedAt(LocalDateTime.now());
//        u1.setSocialPlatformId("1001");
//        u1.setProfileId(101L);
//        u1.setProfilePicture("pic1");
//        u1.setBio("Bio of user one");
//        u1.setIntro("Intro of user one");
//        users.add(u1);
//
//        User u2 = new User();
//        u2.setUserAccount("account2");
//        u2.setUserPassword("pass2");
//        u2.setUserName("User Two");
//        u2.setEmailAddress("two@example.com");
//        u2.setPhoneNumber("0911000002");
//        u2.setAddress("Address 2");
//        u2.setAccountRank("2");
//        u2.setCategory("Category B");
//        u2.setCreatedAt(LocalDateTime.now());
//        u2.setSocialPlatformId("1002");
//        u2.setProfileId(102L);
//        u2.setProfilePicture("pic2");
//        u2.setBio("Bio of user two");
//        u2.setIntro("Intro of user two");
//        users.add(u2);
//
//        User u3 = new User();
//        u3.setUserAccount("account3");
//        u3.setUserPassword("pass3");
//        u3.setUserName("User Three");
//        u3.setEmailAddress("three@example.com");
//        u3.setPhoneNumber("0911000003");
//        u3.setAddress("Address 3");
//        u3.setAccountRank("3");
//        u3.setCategory("Category C");
//        u3.setCreatedAt(LocalDateTime.now());
//        u3.setSocialPlatformId("1003");
//        u3.setProfileId(103L);
//        u3.setProfilePicture("pic3");
//        u3.setBio("Bio of user three");
//        u3.setIntro("Intro of user three");
//        users.add(u3);
//
//        User u4 = new User();
//        u4.setUserAccount("account4");
//        u4.setUserPassword("pass4");
//        u4.setUserName("User Four");
//        u4.setEmailAddress("four@example.com");
//        u4.setPhoneNumber("0911000004");
//        u4.setAddress("Address 4");
//        u4.setAccountRank("4");
//        u4.setCategory("Category D");
//        u4.setCreatedAt(LocalDateTime.now());
//        u4.setSocialPlatformId("1004");
//        u4.setProfileId(104L);
//        u4.setProfilePicture("pic4");
//        u4.setBio("Bio of user four");
//        u4.setIntro("Intro of user four");
//        users.add(u4);
//
//        User u5 = new User();
//        u5.setUserAccount("account5");
//        u5.setUserPassword("pass5");
//        u5.setUserName("User Five");
//        u5.setEmailAddress("five@example.com");
//        u5.setPhoneNumber("0911000005");
//        u5.setAddress("Address 5");
//        u5.setAccountRank("5");
//        u5.setCategory("Category E");
//        u5.setCreatedAt(LocalDateTime.now());
//        u5.setSocialPlatformId("1005");
//        u5.setProfileId(105L);
//        u5.setProfilePicture("pic5");
//        u5.setBio("Bio of user five");
//        u5.setIntro("Intro of user five");
//        users.add(u5);
//
//        // Save all users
//        userRepository.saveAll(users);
//
//        // Print the total count
//        System.out.println("Total users count: " + userRepository.count());
//
//        // Retrieve by account and print email
//        Optional<User> result = userRepository.findByUserAccount("account3");
//        System.out.println("Email for account3: " + result.map(User::getEmailAddress).orElse("Not found"));
//
//        // Existence check and print
//        System.out.println("Does account4 exist? " + userRepository.existsByUserAccount("account4"));
//    }
//}
//
