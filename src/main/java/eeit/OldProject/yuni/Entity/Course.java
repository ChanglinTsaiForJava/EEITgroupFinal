package eeit.OldProject.yuni.Entity;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "course", schema = "final")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String duration;
    private Boolean isProgressLimited;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('daily_care', 'dementia', 'nutrition', 'psychology', 'assistive', 'resource', 'endoflife', 'skills', 'selfcare')")
    private Category category;
    private Integer price;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] coverImage;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
   

    public Course(String title, String description, String duration, Boolean isProgressLimited, Category category, Integer price, byte[] coverImage, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.isProgressLimited = isProgressLimited;
        this.category = category;
        this.price = price;
        this.coverImage = coverImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(Integer courseId, String title, String description, String duration, Boolean isProgressLimited,
			Category category, Integer price, byte[] coverImage, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.courseId = courseId;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.isProgressLimited = isProgressLimited;
		this.category = category;
		this.price = price;
		this.coverImage = coverImage;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Boolean getIsProgressLimited() {
		return isProgressLimited;
	}

	public void setIsProgressLimited(Boolean isProgressLimited) {
		this.isProgressLimited = isProgressLimited;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@JsonIgnore
	public byte[] getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(byte[] coverImage) {
		this.coverImage = coverImage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", title=" + title + ", description=" + description + ", duration="
				+ duration + ", isProgressLimited=" + isProgressLimited + ", category=" + category + ", price=" + price
				+ ", coverImage=" + Arrays.toString(coverImage) + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
    
    
}

