package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "physical")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Physical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhysicalId")
    private Long physicalId;

    @Column(name = "PhysicalName")
    private String physicalName;
}

