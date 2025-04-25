package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "caregiver_service_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaregiverServiceItem {

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
    private ServiceItem serviceItem;
}

