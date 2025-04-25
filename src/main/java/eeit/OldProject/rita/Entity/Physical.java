package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "physical")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Physical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhysicalId")
    private Long physicalId;

    @Column(name = "PhysicalName")
    private String physicalName;

    @Column(name = "PhysicalDescription", columnDefinition = "TEXT", nullable = true)
    private String physicalDescription;
}

