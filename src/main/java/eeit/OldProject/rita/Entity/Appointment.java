package eeit.OldProject.rita.Entity;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eeit.OldProject.steve.Entity.Patient;
import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.yuuhou.Entity.Caregiver;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "PatientId")
    private Long patientId;

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
    @JsonIgnoreProperties("appointments") 
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("appointments") 
    @JoinColumn(name = "CaregiverId", insertable = false, updatable = false)
    private Caregiver caregiver;

    @ManyToOne
    @JsonIgnoreProperties("appointments") 
    @JoinColumn(name = "PatientId", insertable = false, updatable = false)
    private Patient patient;
    
    @JsonIgnore
    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    private Set<AppointmentDisease> diseases;
    
    @JsonIgnore
    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    private Set<AppointmentPhysical> physicalConditions;

    @JsonIgnore
    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    private Set<AppointmentServiceItem> services;
    
    @JsonIgnore
    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    private Set<AppointmentTimeContinuous> timeContinuous;

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

