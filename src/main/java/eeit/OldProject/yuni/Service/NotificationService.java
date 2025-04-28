package eeit.OldProject.yuni.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eeit.OldProject.yuni.Repository.NotificationRepository;

@Service("notificationServiceYuni")
public class NotificationService {

    @Autowired private NotificationRepository notificationRepository;
}
