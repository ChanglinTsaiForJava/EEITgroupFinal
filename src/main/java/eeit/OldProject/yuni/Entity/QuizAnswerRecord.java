package eeit.OldProject.yuni.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_answer_record", schema = "final")

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

	public QuizAnswerRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizAnswerRecord(Integer answerId, QuizSubmission submission, QuizQuestion question, QuizOption option) {
		super();
		this.answerId = answerId;
		this.submission = submission;
		this.question = question;
		this.option = option;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public QuizSubmission getSubmission() {
		return submission;
	}

	public void setSubmission(QuizSubmission submission) {
		this.submission = submission;
	}

	public QuizQuestion getQuestion() {
		return question;
	}

	public void setQuestion(QuizQuestion question) {
		this.question = question;
	}

	public QuizOption getOption() {
		return option;
	}

	public void setOption(QuizOption option) {
		this.option = option;
	}

	@Override
	public String toString() {
		return "QuizAnswerRecord [answerId=" + answerId + ", submission=" + submission + ", question=" + question
				+ ", option=" + option + "]";
	}
    
    
}
