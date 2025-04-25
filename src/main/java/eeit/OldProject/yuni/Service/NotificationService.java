package eeit.OldProject.yuni.Service;

import eeit.OldProject.yuni.Entity.Notification;
import eeit.OldProject.yuni.Repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired private NotificationRepository notificationRepository;
}
