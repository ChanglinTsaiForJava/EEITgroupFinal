package eeit.OldProject.yuni.Controller;

import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.steve.Repository.UserRepository;
import eeit.OldProject.yuni.DTO.UserCourseEnrollDto;
import eeit.OldProject.yuni.Entity.Chapter;
import eeit.OldProject.yuni.Entity.Course;
import eeit.OldProject.yuni.Entity.Progress;
import eeit.OldProject.yuni.Entity.Status;
import eeit.OldProject.yuni.Repository.ChapterRepository;
import eeit.OldProject.yuni.Repository.CourseRepository;
import eeit.OldProject.yuni.Repository.ProgressRepository;
import eeit.OldProject.yuni.Service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ProgressRepository progressRepository;

    // 查使用者在課程的全部章節進度
    @GetMapping("/user/{userId}/course/{courseId}")
    public ResponseEntity<?> getProgressByUserAndCourse(@PathVariable Long userId, @PathVariable Integer courseId){
      Boolean userExists = userRepository.existsById(userId);
      if(!userExists){
        return ResponseEntity.status(404).body("查無此用戶");
      }
      Boolean courseExists = courseRepository.existsById(courseId);
      if(!courseExists){
          return ResponseEntity.status(404).body("查無此課程");
      }

      List<Progress> progressList = progressService.getProgressByUserAndCourse(userId, courseId);
      if (!progressList.isEmpty()) {
          return ResponseEntity.ok(progressList);
      } else {
          return ResponseEntity.status(404).body("找不到使用者 " + userId + " 在第 " + courseId + " 課程中的進度資料");
      }

    }

    // 茶特定使用者在某課程的全部章節進度
//    @GetMapping("/user/{userId}/course/{courseId}")
//    public ResponseEntity<?> getProgressByUserAndCourse(@PathVariable Long userId, @PathVariable Integer courseId) {
//        List<Progress> progressList = progressService.getProgressByUserAndCourse(userId, courseId);
//        if (!progressList.isEmpty()) {
//            return ResponseEntity.ok(progressList);
//        } else {
//            return ResponseEntity.status(404).body("找不到使用者 " + userId + " 在課程 " + courseId + " 的任何進度資料");
//        }
//    }




    //茶特定使用者在某章節的進度
    @GetMapping("/user/{userId}/chapter/{chapterId}/progress")
    public ResponseEntity<?> getProgressByUserAndChapter(@PathVariable Long userId, @PathVariable Integer chapterId) {
        Boolean userExists = userRepository.existsById(userId);
        if(!userExists){
            return ResponseEntity.status(404).body("查無此用戶");
        }
        Boolean chapterExists = chapterRepository.existsById(chapterId);
        if(!chapterExists){
            return ResponseEntity.status(404).body("查無此章節");
        }

        Optional<Progress> progressOpt = progressService.getProgressByUserAndChapter(userId, chapterId);
        if (!progressOpt.isEmpty()) {
            return ResponseEntity.ok(progressOpt);
        } else {
            return ResponseEntity.status(404).body("找不到使用者 " + userId + " 在第 " + chapterId + " 章的任何進度資料");
        }
          }

    //首次點進章節時建立一筆新的進度
//    @PostMapping("/chapter/{chapterId}")
//    public ResponseEntity<?> createProgress(@PathVariable Integer chapterId,
//                                            @RequestParam Long userId,
//                                            @RequestParam Integer courseId) {
//        Progress created = progressService.createProgress(userId, courseId, chapterId);
//        created.setStatus(Status.in_progress);
//        created.setIsCompleted(false);
//        return ResponseEntity.ok(created);
//    }

//    @GetMapping("/user/{userId}/chapter/{chapterId}/with-create")
//    public ResponseEntity<?> getOrCreateProgress(@PathVariable Long userId,
//                                                 @PathVariable Integer chapterId,
//                                                 @RequestParam(required = false) Integer courseId) {
//        if (!userRepository.existsById(userId)) {
//            return ResponseEntity.status(404).body("查無此用戶");
//        }
//        if (!chapterRepository.existsById(chapterId)) {
//            return ResponseEntity.status(404).body("查無此章節");
//        }
//        Optional<Progress> progressOpt = progressService.getProgressByUserAndChapter(userId, chapterId);
//        Progress progress = progressOpt.orElseGet(() -> progressService.createProgress(userId, courseId, chapterId));
//        return ResponseEntity.ok(progress);
//    }

    @GetMapping("/user/{userId}/chapter/{chapterId}/with-create")
    public ResponseEntity<?> getOrCreateProgress(@PathVariable Long userId,
                                                 @PathVariable Integer chapterId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(404).body("查無此用戶");
        }
        Optional<Chapter> chapterOpt = chapterRepository.findById(chapterId);
        if (chapterOpt.isEmpty()) {
            return ResponseEntity.status(404).body("查無此章節");
        }

        Optional<Progress> progressOpt = progressService.getProgressByUserAndChapter(userId, chapterId);
        if (progressOpt.isPresent()) {
            return ResponseEntity.ok(progressOpt.get());
        } else {
            Chapter chapter = chapterOpt.get();
            if (chapter.getCourse() == null || chapter.getCourse().getCourseId() == null) {
                return ResponseEntity.status(400).body("章節沒有對應課程，無法建立進度！");
            }
            Integer courseId = chapter.getCourse().getCourseId(); 

            Progress createdProgress = progressService.createProgress(userId, courseId, chapterId);
            return ResponseEntity.status(201).body(createdProgress);
        }
    }


    // 更新觀看進度 + 完成狀態
    @PatchMapping("/chapter/{chapterId}/progress")
    public ResponseEntity<?> updateProgress(@PathVariable Integer chapterId,
                                            @RequestParam Long userId,
                                            @RequestParam(required = false) Float lastWatched,
                                            @RequestParam(required = false) Boolean isCompleted) {
        Optional<Progress> progressOpt = progressService.getProgressByUserAndChapter(userId, chapterId);
        if (progressOpt.isPresent()) {
            Progress updated = progressService.markChapterProgress(progressOpt.get(), lastWatched, isCompleted);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("找不到進度記錄，請先建立！");
        }
    }

    // 使用者手動「標記章節完成」
    @PatchMapping("/user/{userId}/chapter/{chapterId}/complete")
    public ResponseEntity<?> completeChapter(@PathVariable Integer chapterId,
                                             @RequestParam Long userId) {
        Optional<Progress> progressOpt = progressService.getProgressByUserAndChapter(userId, chapterId);
        if (progressOpt.isPresent()) {
            Progress completed = progressService.completeChapter(progressOpt.get());
            return ResponseEntity.ok(completed);
        } else {
            return ResponseEntity.status(404).body("找不到進度記錄，請先建立！");
        }
    }

    // 判斷課全部完成
    @GetMapping("/user/{userId}/course/{courseId}/comIdpleted")
    public ResponseEntity<?> checkCourseCompletion(@PathVariable Long userId, @PathVariable Integer courseId) {
        boolean completed = progressService.isCourseCompleted(userId, courseId);
        return ResponseEntity.ok(completed);
    }

    @PatchMapping("/user/{userId}/chapter/{chapterId}/last-watched")
    public ResponseEntity<?> updateLastWatched(@PathVariable Long userId,
                                               @PathVariable Integer chapterId,
                                               @RequestParam Float lastWatched) {
        Optional<Progress> progressOpt = progressService.getProgressByUserAndChapter(userId, chapterId);
        if (progressOpt.isPresent()) {
            Progress updated = progressService.updateLastWatchedOnly(progressOpt.get(), lastWatched);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("找不到進度記錄，請先進入章節建立！");
        }
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollCourse(@RequestBody UserCourseEnrollDto dto) {
        Long userId = dto.getUserId();
        Integer courseId = dto.getCourseId();

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (userOpt.isEmpty() || courseOpt.isEmpty()) {
            return ResponseEntity.status(404).body("用戶或課程不存在");
        }

        boolean alreadyEnrolled = progressRepository.existsByUserId_UserIdAndCourseId_CourseId(userId, courseId);
        if (alreadyEnrolled) {
            return ResponseEntity.status(409).body("你已經加入過這門課了");
        }

        List<Chapter> chapters = chapterRepository.findByCourse_CourseIdOrderByPositionAsc(courseId);
        if (chapters.isEmpty()) {
            return ResponseEntity.status(400).body("此課程尚未有章節，無法加入");
        }

        Chapter firstChapter = chapters.get(0);

        Progress progress = new Progress();
        progress.setUserId(userOpt.get());
        progress.setCourseId(courseOpt.get());
        progress.setChapterId(firstChapter);
        progress.setStatus(Status.not_started);
        progress.setIsCompleted(false);
        progress.setLastWatched(0f);
        progress.setCompletionDate(null);

        progressRepository.save(progress);

        return ResponseEntity.ok("已成功加入課程！");
    }


//    @GetMapping("/user/{userId}/my-courses")
//    public ResponseEntity<?> getMyCourses(@PathVariable Long userId) {
//        List<Course> courses = progressRepository.findDistinctCoursesByUserId(userId);
//        return ResponseEntity.ok(courses);
//    }

    @GetMapping("/user/{userId}/my-courses")
    public ResponseEntity<?> getMyCourses(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(404).body("查無此用戶");
        }
        List<Course> courses = progressRepository.findDistinctCoursesByUserId(userId);
        return ResponseEntity.ok(courses);
    }


    @DeleteMapping("/user/{userId}/course/{courseId}")
    public ResponseEntity<?> deleteProgressByUserAndCourse(@PathVariable Long userId, @PathVariable Integer courseId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(404).body("查無此用戶");
        }
        if (!courseRepository.existsById(courseId)) {
            return ResponseEntity.status(404).body("查無此課程");
        }

        List<Progress> progresses = progressRepository.findByUserId_UserIdAndCourseId_CourseId(userId, courseId);
        if (progresses.isEmpty()) {
            return ResponseEntity.status(404).body("找不到此使用者與課程的進度資料");
        }

        progressRepository.deleteAll(progresses);
        return ResponseEntity.ok("已成功移除課程");
    }

}
