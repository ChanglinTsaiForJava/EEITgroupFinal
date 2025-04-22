package eeit.OldProject.daniel.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eeit.OldProject.steve.Entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user","reply","reportType","resolver"})
@Entity
@Table(name = "reply_report", schema = "final")
public class ReplyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReplyReportId")
    private Long replyReportId;

    @Column(name = "Reason", length = 400)
    private String reason;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "IsResolved")
    private Boolean isResolved;

    @Column(name = "ResolvedAt")
    private LocalDateTime resolvedAt;

    @Column(name = "ResolutionNote", length = 400)
    private String resolutionNote;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "ReplyId")
    private Long replyId;

    @Column(name = "ReportTypeId")
    private Byte reportTypeId;

    @Column(name = "ResolvedBy")
    private Long resolvedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", insertable = false, updatable = false)
    @JsonIgnoreProperties("replyReports")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ReplyId", insertable = false, updatable = false)
    @JsonIgnoreProperties("reports")
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ReportTypeId", insertable = false, updatable = false)
    @JsonIgnoreProperties({"commentReports","postReports","replyReports"})
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ResolvedBy", insertable = false, updatable = false)
    @JsonIgnoreProperties({"commentReports","postReports","replyReports"})
    private User resolver;
}