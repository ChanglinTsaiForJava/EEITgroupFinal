package eeit.OldProject.daniel.entity.useless;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = {"commentReports","postReports","replyReports"})
@Entity
@Table(name = "report_type", schema = "final")
public class ReportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportTypeId")
    private Byte reportTypeId;

    @Column(name = "Type", length = 100)
    private String type;

    @Column(name = "Description", length = 400)
    private String description;

    @OneToMany(mappedBy = "reportType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("reportType")
    private List<CommentReport> commentReports;

    @OneToMany(mappedBy = "reportType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("reportType")
    private List<PostReport> postReports;

    @OneToMany(mappedBy = "reportType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("reportType")
    private List<ReplyReport> replyReports;
}
