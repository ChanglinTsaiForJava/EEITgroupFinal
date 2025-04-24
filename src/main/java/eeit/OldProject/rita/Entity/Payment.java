package eeit.OldProject.rita.Entity;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentId")
    private Long paymentId;

    @Column(name = "ReferenceId")
    private Integer referenceId;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "Amount")
    private BigDecimal amount;

    @Column(name = "PaymentMethod")
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "PaymentStatus")
    private PaymentStatus paymentStatus;

    @Column(name = "EcpayTransactionId")
    private String ecpayTransactionId;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @Column(name = "MerchantTradeNo")
    private String merchantTradeNo;

    @Column(name = "TradeDate")
    private Date tradeDate;

    @Column(name = "PaymentErrorMessage")
    private String paymentErrorMessage;

    @Column(name = "RedeemPoints")
    private Integer redeemPoints;

    @Column(name = "FinalAmount")
    private BigDecimal finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "PaymentReferenceType")
    private ReferenceType paymentReferenceType;

    @Column(name = "PointsEarned")
    private Integer pointsEarned;

    @Column(name = "TradeDesc")
    private String tradeDesc;

    @Column(name = "ItemName")
    private String itemName;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    private User user;
}

enum PaymentStatus {
    Pending,
    Paid,
    Failed
}

enum PaymentReferenceType {
    Appointment,
    Order
}

