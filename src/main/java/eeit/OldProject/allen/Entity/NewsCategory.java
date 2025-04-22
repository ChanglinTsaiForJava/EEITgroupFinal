package eeit.OldProject.allen.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties("newsList")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news_category")
public class NewsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private Integer categoryId;

    @Column(name = "CategoryName", nullable = false, length = 100)
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<News> newsList;
}