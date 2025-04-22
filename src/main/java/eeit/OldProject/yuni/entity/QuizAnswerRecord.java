package eeit.OldProject.yuni.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_answer_record", schema = "final")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswerRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    @ManyToOne
    @JoinColumn(name = "SubmissionId", referencedColumnName = "SubmissionId")
    private QuizSubmission submission;

    @ManyToOne
    @JoinColumn(name = "QuestionId", referencedColumnName = "QuestionId")
    private QuizQuestion question;

    @ManyToOne
    @JoinColumn(name = "OptionId", referencedColumnName = "OptionId")
    private QuizOption option;
}
