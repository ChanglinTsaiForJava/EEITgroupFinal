package eeit.OldProject.rita.Entity;

import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.yuuhou.Entity.Caregiver;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name= "appointment")
@Data // 👉 @Data = 自動生成 getter、setter、toString、equals、hashCode
@Builder // 👉 @Builder = 使用建構器模式建立物件
@NoArgsConstructor // 👉 @NoArgsConstructor = 無參數建構子
@AllArgsConstructor // 👉 @AllArgsConstructor = 全參數建構子
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentId")
    private Long appointmentId;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "CaregiverId")
    private Long caregiverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TimeType",nullable = true)
    private TimeType timeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    public AppointmentStatus status;

    @Column(name = "TotalPrice" , precision = 10, scale = 2, nullable = true)
    private BigDecimal totalPrice;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "LocationType", nullable = false)
    private LocationType locationType;

    // Hospital-specific fields
    @Column(name = "HospitalName", length = 100, nullable = true)
    private String hospitalName;

    @Column(name = "HospitalAddress", length = 255, nullable = true)
    private String hospitalAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "HospitalWardType", nullable = false)
    private HospitalWardType hospitalWardType;

    @Column(name = "HospitalWardNumber", length = 50, nullable = true)
    private String hospitalWardNumber;

    @Column(name = "HospitalBedNumber", length = 50, nullable = true)
    private String hospitalBedNumber;

    @Column(name = "HospitalDepartment", length = 100, nullable = true)
    private String hospitalDepartment;

    @Column(name = "HospitalTransportNote", columnDefinition = "TEXT", nullable = true)
    private String hospitalTransportNote;

    // Home-specific fields
    @Column(name = "HomeAddress", length = 255, nullable = true)
    private String homeAddress;

    @Column(name = "HomeTransportNote", columnDefinition = "TEXT", nullable = true)
    private String homeTransportNote;

    // 合約確認欄位
    @Column(name = "contractConfirmed", nullable = false)
    private boolean contractConfirmed = false;


    // Relationships
    @ManyToOne
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "CaregiverId", insertable = false, updatable = false)
    private Caregiver caregiver;

    public static enum AppointmentStatus {
        Pending,
        CaregiverConfirmed,
        Paid,
        Completed,
        Cancelled
    }

}
enum TimeType {
    continuous,
    multi
}

enum HospitalWardType {
    一般病房,
    急診室
}

