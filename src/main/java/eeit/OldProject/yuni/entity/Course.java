package eeit.OldProject.yuni.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "course", schema = "final")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
