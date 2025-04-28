package eeit.OldProject.allen.Service;

import java.util.List;

import eeit.OldProject.allen.Entity.Announcement;

public interface AnnouncementService {
	//基本CRUD
	Announcement findById(Integer id);
	List<Announcement> findAll();
	Announcement save(Announcement announcement);
}
