package eeit.OldProject.allen.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eeit.OldProject.allen.Entity.Announcement;
import eeit.OldProject.allen.Entity.AnnouncementCategory;
import eeit.OldProject.allen.Repository.AnnouncementCategoryRepository;
import eeit.OldProject.allen.Repository.AnnouncementRepository;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

	@Autowired
	private AnnouncementRepository announcementRepository;

	@Autowired
	private AnnouncementCategoryRepository categoryRepository;

	@Override
	public Announcement findById(Integer id) {
		Optional<Announcement> optional = announcementRepository.findById(id);
		return optional.orElse(null);
	}

	@Override
	public List<Announcement> findAll() {
		return announcementRepository.findAll();
	}
	
	@Override
	public Announcement save(Announcement announcement) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + announcement.getCategory());
	    // 1. 檢查是否有帶 category 和 categoryId
	    if (announcement.getCategory() == null || announcement.getCategory().getCategoryId() == null) {
	        throw new IllegalArgumentException("必須提供有效的 CategoryId");
	    }

	    // 2. 取得 categoryId
	    Integer categoryId = announcement.getCategory().getCategoryId();

	    // 3. 查詢資料庫中是否真的有這個分類
	    AnnouncementCategory category = categoryRepository.findById(categoryId)
	            .orElseThrow(() -> new IllegalArgumentException("找不到 CategoryId: " + categoryId));

	    // 4. 把完整的 Category 實體塞回 announcement
	    announcement.setCategory(category);

	    // 5. 如果沒有傳 status，就預設是草稿 0
	    if (announcement.getStatus() == null) {
	        announcement.setStatus(0); // 預設草稿
	    }

	    // 6. 如果沒有傳 createAt，就預設當下時間
	    if (announcement.getCreateAt() == null) {
	        announcement.setCreateAt(LocalDateTime.now());
	    }

	    // 7. 儲存公告
	    return announcementRepository.save(announcement);
	}
}
