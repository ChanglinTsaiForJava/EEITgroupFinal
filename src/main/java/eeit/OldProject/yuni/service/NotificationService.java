package eeit.OldProject.yuni.service;

import eeit.OldProject.yuni.entity.Notification;
import eeit.OldProject.yuni.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired private NotificationRepository notificationRepository;
}
