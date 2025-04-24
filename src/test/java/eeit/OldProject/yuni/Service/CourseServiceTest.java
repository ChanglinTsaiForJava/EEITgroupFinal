package eeit.OldProject.yuni.Service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import eeit.OldProject.yuni.Entity.Category;
import eeit.OldProject.yuni.Entity.Course;

@SpringBootTest
public class CourseServiceTest {

	@Autowired
	private CourseService courseService;

//	public List<Course> getAllCourses() {
//	return courseRepository.findAll();
//}
	@Test
	void testGetAllCourses() {
		List<Course> courses = courseService.getAllCourses();
		for (Course course : courses) {
			System.out.println("課程標題：" + course.getTitle());
		}
		System.out.println("找到的課程數量：" + courses.size());
		System.out.println("==========================================================");
	}

//	public Optional<Course> getCourseById(Integer id) {
//		return courseRepository.findById(id);
//	}
	@Test
	void testGetCourseById() {
		// 資料庫有這筆
		Optional<Course> course = courseService.getCourseById(5);
		if (course.isPresent()) {
			System.out.println("課程 id 為 5 的課程為：" + course.get().getTitle());
		} else {
			System.out.println("找不到課程 id 為 5 的課程！");
		}
		// 資料庫沒有這筆
		Optional<Course> course2 = courseService.getCourseById(1000);
		if (course2.isPresent()) {
			System.out.println("課程 id 為 1000 的課程為：" + course2.get().getTitle());
		} else {
			System.out.println("找不到課程 id 為 1000 的課程！");
		}
		System.out.println("==========================================================");
	}

//	public List<Course> searchCoursesByKeyword(String keyword) {
//		return courseRepository.searchByKeyword(keyword);
//	}

	@Test
	void testSearchCoursesByKeyword() {
		List<Course> courses = courseService.searchCoursesByKeyword("照護");
		if (courses.isEmpty()) {
			System.out.println("沒有包含照護的課程");
		} else {
			for (Course course : courses) {
				System.out.println("包含照護的課程：" + course.getTitle());
			}
		}

		List<Course> courses2 = courseService.searchCoursesByKeyword("美食");
		if (!courses2.isEmpty()) {
			for (Course course : courses2) {
				System.out.println("包含美食的課程：" + course.getTitle());
			}
		} else {
			System.out.println("沒有包含美食的課程");
		}
		System.out.println("==========================================================");
	}

//	public List<Course> getCoursesByCategory(Category category) {
//		return courseRepository.findByCategory(category);
//	}

	@Test
	void testGetCoursesByCategory() {
		List<Course> result = courseService.getCoursesByCategory(Category.daily_care);
		System.out.println("查詢 daily_care 課程數量：" + result.size());
		System.out.println("==========================================================");
	}

//	public Course createCourse(Course course) {
//		return courseRepository.save(course);
//	}
	@Test
	void testCreateCourse() {
		Course course = new Course();
		course.setTitle("單元測試課程");
		course.setDescription("這是一門測試用課程");
		course.setDuration("30分鐘");
		course.setCategory(Category.daily_care);
		course.setPrice(100);
		course.setIsProgressLimited(false);

		Course saved = courseService.createCourse(course);
		System.out.println("新增成功，課程 ID：" + saved.getCourseId());
		System.out.println("==========================================================");
	}

//	public Course updateCourse(Integer id, Course course) {
//		course.setCourseId(id);
//		return courseRepository.save(course);
//	}

	@Test
	void testUpdateCourse() {
		Course course = new Course();
		course.setTitle("更新前的課程");
		course.setDescription("準備更新");
		course.setDuration("20分鐘");
		course.setCategory(Category.nutrition);
		course.setPrice(200);
		course.setIsProgressLimited(true);
		Course saved = courseService.createCourse(course);
		System.out.println("更新前的課程，標題：" + saved.getTitle());

		Course updatedInfo = new Course();
		updatedInfo.setTitle("更新後的課程");
		updatedInfo.setDescription("已更新");
		updatedInfo.setDuration("25分鐘");
		updatedInfo.setCategory(Category.psychology);
		updatedInfo.setPrice(300);
		updatedInfo.setIsProgressLimited(false);

		Course updated = courseService.updateCourse(saved.getCourseId(), updatedInfo);
		System.out.println("課程更新成功，標題：" + updated.getTitle());
		System.out.println("==========================================================");
	}

//	public void deleteCourse(Integer id) {
//		courseRepository.deleteById(id);
//	}

	@Test
	void testDeleteCourse() {
		courseService.deleteCourse(15);
		Optional<Course> deleted = courseService.findById(15);
		if (deleted.isEmpty()) {
			System.out.println("課程已成功刪除");
		} else {
			System.out.println("課程刪除失敗：" + deleted.get().getTitle());
		}

		System.out.println("==========================================================");
	}

	// public Optional<Course> findById(Integer id) {
//   return courseRepository.findById(id);
//}
	@Test
	void testFindById() {

		// 測試查詢存在的課程
		Optional<Course> found = courseService.findById(10);
		if (found.isPresent()) {
			System.out.println("查詢成功，課程標題：" + found.get().getTitle());
		} else {
			System.out.println("找不到課程");
		}

		// 測試查詢不存在的課程
		Optional<Course> notFound = courseService.findById(1000);
		if (notFound.isPresent()) {
			System.out.println("查詢成功，課程標題：" + found.get().getTitle());
		} else {
			System.out.println("找不到課程");
		}
		System.out.println("==========================================================");
	}

}
