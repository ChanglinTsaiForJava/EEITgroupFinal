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
@ToString(exclude = {"reportedBy","comment","reportType","resolver"})
@Entity
@Table(name = "comment_report", schema = "final")
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentReportId")
    private Long commentReportId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    @JsonIgnoreProperties("commentReports")
    private User reportedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CommentId")
    @JsonIgnoreProperties("reports")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ReportTypeId")
    @JsonIgnoreProperties({"commentReports","postReports","replyReports"})
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ResolvedBy")
    @JsonIgnoreProperties({"commentReports","postReports","replyReports"})
    private User resolver;
}
