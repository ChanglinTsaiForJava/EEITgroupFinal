package eeit.OldProject.allen.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.allen.Entity.Announcement;
import eeit.OldProject.allen.Entity.AnnouncementCategory;
import eeit.OldProject.allen.Repository.AnnouncementCategoryRepository;
import eeit.OldProject.allen.Repository.AnnouncementRepository;

@RestController
@RequestMapping("/test")
public class TestEntityController {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementCategoryRepository announcementCategoryRepository;

    // 查詢全部公告
    @GetMapping("/announcements")
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    // 查詢全部分類
    @GetMapping("/categories")
    public List<AnnouncementCategory> getAllCategories() {
        return announcementCategoryRepository.findAll();
    }
}
