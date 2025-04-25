package eeit.OldProject.yuni.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import eeit.OldProject.yuni.Service.NotificationService;


	
@Controller
public class NotificationController{

	    @Autowired
	  private NotificationService notificationService;
	}

