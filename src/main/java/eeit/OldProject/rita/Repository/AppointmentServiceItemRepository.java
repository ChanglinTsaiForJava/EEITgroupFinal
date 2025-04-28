package eeit.OldProject.rita.Repository;

import eeit.OldProject.rita.Entity.AppointmentServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentServiceItemRepository extends JpaRepository<AppointmentServiceItem, Long> {

    /** 根據 AppointmentId 刪除所有對應的 AppointmentServiceItem **/
    void deleteByAppointmentId(Long appointmentId);




}
