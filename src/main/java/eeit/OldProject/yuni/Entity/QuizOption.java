package eeit.OldProject.yuni.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_option", schema = "final")

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

		public QuizOption() {
			super();
			// TODO Auto-generated constructor stub
		}

		public QuizOption(Integer optionId, String optionText, Boolean isCorrect, QuizQuestion questionId) {
			super();
			this.optionId = optionId;
			this.optionText = optionText;
			this.isCorrect = isCorrect;
			this.questionId = questionId;
		}

		public Integer getOptionId() {
			return optionId;
		}

		public void setOptionId(Integer optionId) {
			this.optionId = optionId;
		}

		public String getOptionText() {
			return optionText;
		}

		public void setOptionText(String optionText) {
			this.optionText = optionText;
		}

		public Boolean getIsCorrect() {
			return isCorrect;
		}

		public void setIsCorrect(Boolean isCorrect) {
			this.isCorrect = isCorrect;
		}

		public QuizQuestion getQuestionId() {
			return questionId;
		}

		public void setQuestionId(QuizQuestion questionId) {
			this.questionId = questionId;
		}

		@Override
		public String toString() {
			return "QuizOption [optionId=" + optionId + ", optionText=" + optionText + ", isCorrect=" + isCorrect
					+ ", questionId=" + questionId + "]";
		}
        
        

}
