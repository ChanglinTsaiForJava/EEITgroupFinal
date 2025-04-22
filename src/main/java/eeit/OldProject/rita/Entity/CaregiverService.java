package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "caregiver_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaregiverService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CaregiverId")
    private Long caregiverId;

    @Column(name = "ServiceId")
    private Long serviceId;

    // Relationships
//    @ManyToOne
//    @JoinColumn(name = "CaregiverId", insertable = false, updatable = false)
//    private Caregiver caregiver;

    @ManyToOne
    @JoinColumn(name = "ServiceId", insertable = false, updatable = false)
    private Service service;
}

