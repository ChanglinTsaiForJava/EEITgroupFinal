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
@Table(name = "customer_inquiry")
public class CustomerInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InquiryId")
    private Long inquiryId;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "Caregiver")
    private Long caregiverId;

    @Column(name = "Email")
    private String email;

    @Column(name = "InquiryText", columnDefinition = "TEXT")
    private String inquiryText;

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Column(name = "Status")
    private String status;

    @Column(name = "ResponseText", columnDefinition = "TEXT")
    private String responseText;

    @Column(name = "ResponseDate")
    private LocalDateTime responseDate;
}



