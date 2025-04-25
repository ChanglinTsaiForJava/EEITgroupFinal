package eeit.OldProject.yuni.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eeit.OldProject.yuni.Entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
