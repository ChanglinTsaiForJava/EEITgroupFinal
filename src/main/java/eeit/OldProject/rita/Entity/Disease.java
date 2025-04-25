package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "disease")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiseaseId")
    private Long diseaseId;

    @Column(name = "DiseaseName", length = 50, nullable = true)
    private String diseaseName;

    @Column(name = "DiseaseDescription" , columnDefinition = "TEXT", nullable = true)
    private String DiseaseDescription;
}

