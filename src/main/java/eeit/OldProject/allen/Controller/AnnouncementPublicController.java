package eeit.OldProject.allen.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import eeit.OldProject.allen.Entity.Announcement;
import eeit.OldProject.allen.Service.AnnouncementService;

@RestController
@RequestMapping("/announcement/public")
public class AnnouncementPublicController {
	
	@Autowired
	private AnnouncementService announcementService;
	
	//查詢一筆公告
	@GetMapping("/{id}")
	public Announcement getAnnouncementById(@PathVariable Integer id) {
	    Announcement announcement = announcementService.findById(id);
	    if(announcement == null) {
	    	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到這筆公告");
	    }
	    return announcement;
	}
	
	//查詢所有公告
	@GetMapping
	public List<Announcement> getAllAnnouncement() {
		return announcementService.findAll();
	}
}
