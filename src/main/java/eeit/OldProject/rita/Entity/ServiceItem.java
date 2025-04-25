package eeit.OldProject.rita.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ServiceId")
    private Long serviceId;

    @Column(name = "ServiceName", length = 50, nullable = true)
    private String serviceName;

    @Column(name = "ServiceDescription", columnDefinition = "TEXT", nullable = true)
    private String serviceDescription;
}

