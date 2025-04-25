package eeit.OldProject.yuni.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="quiz_question", schema = "final")

public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Column(columnDefinition = "TEXT")
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "CourseId", referencedColumnName = "CourseId")
    private Course course;

	public QuizQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizQuestion(Integer questionId, QuestionType questionType, String questionText, Course course) {
		super();
		this.questionId = questionId;
		this.questionType = questionType;
		this.questionText = questionText;
		this.course = course;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "QuizQuestion [questionId=" + questionId + ", questionType=" + questionType + ", questionText="
				+ questionText + ", course=" + course + "]";
	}
    
    

}

