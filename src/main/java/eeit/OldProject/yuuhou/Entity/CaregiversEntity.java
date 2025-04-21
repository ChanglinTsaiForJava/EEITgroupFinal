package eeit.OldProject.yuuhou.Entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
public class CaregiversEntity {

    @Id // 👉 主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 👉 對應 AUTO_INCREMENT
    private Long caregiverId;

    @Column(nullable = false, length = 100)
    private String caregiverName;

    @Column(nullable = false, length = 255)
    private String photoPath;

    @Column(nullable = false, length = 20)
    private String gender;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    private String phone;

    private String nationality;

    private String languages;

    @Column(nullable = false)
    private Integer yearOfExperience = 0;

    @Column(nullable = false, length = 100)
    private String serviceArea;

    @Lob // 👉 @Lob 代表 large object（對應 TEXT 類型）
    private String description;

    private LocalDateTime reminder;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal halfDayRate = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fullDayRate = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING) // 👉 ENUM 類型，使用字串對應資料庫值
    @Column(nullable = false, length = 10)
    private Status status = Status.ACTIVE;

    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // ENUM 狀態類別
    public enum Status {
        ACTIVE,   // 啟用中
        INACTIVE  // 停用
    }
}
