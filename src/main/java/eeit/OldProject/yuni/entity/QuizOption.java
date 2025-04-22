package eeit.OldProject.yuni.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_option", schema = "final")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizOption {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer optionId;

        @Column(columnDefinition = "TEXT")
        private String optionText;

        private Boolean isCorrect;

        @ManyToOne
        @JoinColumn(name = "QuestionId", referencedColumnName = "QuestionId")
        private QuizQuestion questionId;

}
