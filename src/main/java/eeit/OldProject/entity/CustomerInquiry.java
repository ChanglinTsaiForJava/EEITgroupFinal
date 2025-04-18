package eeit.OldProject.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_inquiry")
public class CustomerInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inquiryID;


    private Integer userId;

    private Integer caregiver;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String inquiryText;

    private LocalDateTime createdDate;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String responseText;

    private LocalDateTime responseDate;

    // Getter 和 Setter 省略，請補上
}
