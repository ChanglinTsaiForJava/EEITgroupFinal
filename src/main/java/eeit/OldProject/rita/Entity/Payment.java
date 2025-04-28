package eeit.OldProject.rita.Entity;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "payment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentId")
    private Long paymentId;

    @Column(name = "ReferenceId", nullable = false)
    private Integer referenceId;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "Amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "PaymentMethod", length = 50, nullable = false)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "PaymentStatus", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "EcpayTransactionId", length = 255, nullable = true)
    private String ecpayTransactionId;

    @Column(name = "CreatedAt", nullable = true)
    private Timestamp createdAt;

    @Column(name = "MerchantTradeNo", length = 20, nullable = false)
    private String merchantTradeNo;

    @Column(name = "TradeDate", nullable = false)
    private Date tradeDate;

    @Column(name = "PaymentErrorMessage" ,columnDefinition = "TEXT", nullable = true)
    private String paymentErrorMessage;

    @Column(name = "RedeemPoints", nullable = true)
    private Integer redeemPoints;

    @Column(name = "FinalAmount", precision = 10, scale = 2, nullable = false)
    private BigDecimal finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "PaymentReferenceType", nullable = false)
    public PaymentReferenceType paymentReferenceType;

    @Column(name = "PointsEarned", nullable = true)
    private Integer pointsEarned;

    @Column(name = "TradeDesc", length = 200, nullable = true)
    private String tradeDesc;

    @Column(name = "ItemName", length = 200, nullable = true)
    private String itemName;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    private User user;
}

