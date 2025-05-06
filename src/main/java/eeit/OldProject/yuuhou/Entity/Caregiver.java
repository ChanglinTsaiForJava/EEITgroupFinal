package eeit.OldProject.yuuhou.Entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 👉 @Data = 自動生成 getter、setter、toString、equals、hashCode
@NoArgsConstructor // 👉 @NoArgsConstructor = 無參數建構子
@AllArgsConstructor // 👉 @AllArgsConstructor = 全參數建構子
@Builder // 👉 @Builder = 使用建構器模式建立物件
@Entity // 👉 @Entity = 表示這是一個資料庫實體類別
@Table(name = "caregivers") // 👉 指定對應資料表名稱
public class Caregiver {

	@Builder.Default
	  @OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<CaregiverComment> comments = new ArrayList<>();

	@Builder.Default
	    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<CaregiverLicense> licenses = new ArrayList<>();
	  
	@Builder.Default
	    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<CaregiverStatistics> statistics = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LoginLog> loginLogs = new ArrayList<>();
	
	@Builder.Default
	    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<PaymentRecord> paymentRecords = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ServiceRecord> serviceRecords = new ArrayList<>();
	
//        Rita ???
//	    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.ALL, orphanRemoval = true)
//	    private List<NotificationService> notifications = new ArrayList<>();

	
	
    @Id // 👉 主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 👉 對應 AUTO_INCREMENT
    @Column(name="CaregiverId")
    private Long caregiverId;

    @Column(nullable = false, length = 100,name="CaregiverName")
    private String caregiverName;

    @Lob
    @Column(name = "photo")
    private byte[] photo;
    
//    @Column(nullable = false, length = 255,name="PhotoPath")
//    private String photoPath;

    @Column(nullable = false, length = 20,name="Gender")
    private String gender;

    @Column(nullable = false,name="Birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(nullable = false, unique = true, length = 100,name="Email")
    private String email;

    @Column(nullable = false, length = 255,name="Password")
    private String password;

    @Column(name="Phone")
    private String phone;

    @Column(name="Nationality")
    private String nationality;

    @Column(name="Languages")
    private String languages;

    @Column(nullable = false,name="YearOfExperience")
    private Integer yearOfExperience = 0;

    @Column(nullable = false, length = 100,name="ServiceCity")
    private String serviceCity;

    @Column(nullable = false, length = 100,name="ServiceDistrict")
    private String serviceDistrict;

    @Lob // 👉 @Lob 代表 large object（對應 TEXT 類型）
    @Column(name="Description",columnDefinition = "TEXT")
    private String description;

    @Column(name="Reminder")
    private LocalDateTime reminder;



    @Column(nullable = false, precision = 10, scale = 2,name="HourlyRate")
    private BigDecimal hourlyRate = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2,name="halfDayRate")
    private BigDecimal halfDayRate = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2,name="FullDayRate")
    private BigDecimal fullDayRate = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING) // 👉 ENUM 類型，使用字串對應資料庫值
    @Column(nullable = false, length = 10,name="Status")
    private Status status = Status.ACTIVE;
    
    @Column(nullable = false)
    private boolean isVerified = false;

    @Column(nullable = false, name = "Created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    

    // ENUM 狀態類別
    public enum Status {
        ACTIVE,   // 啟用中
        INACTIVE  // 停用
    }
    
}
