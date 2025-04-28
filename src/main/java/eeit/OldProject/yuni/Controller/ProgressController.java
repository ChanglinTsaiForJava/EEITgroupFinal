package eeit.OldProject.yuni.Controller;

import eeit.OldProject.yuni.Entity.Progress;
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

    // 茶特定使用者在某課程的全部章節進度
    @GetMapping("/user/{userId}/course/{courseId}")
    public ResponseEntity<?> getProgressByUserAndCourse(@PathVariable Long userId, @PathVariable Integer courseId) {
        List<Progress> progressList = progressService.getProgressByUserAndCourse(userId, courseId);
        if (!progressList.isEmpty()) {
            return ResponseEntity.ok(progressList);
        } else {
            return ResponseEntity.status(404).body("找不到使用者 " + userId + " 在課程 " + courseId + " 的任何進度資料");
        }
    }

    //茶特定使用者在某章節的進度
    @GetMapping("/user/{userId}/chapter/{chapterId}")
    public ResponseEntity<?> getProgressByUserAndChapter(@PathVariable Long userId, @PathVariable Integer chapterId) {
        Optional<Progress> progressOpt = progressService.getProgressByUserAndChapter(userId, chapterId);
        if (!progressOpt.isEmpty()) {
            return ResponseEntity.ok(progressOpt);
        } else {
            return ResponseEntity.status(404).body("找不到使用者 " + userId + " 在章節 " + chapterId + " 的任何進度資料");
        }
          }

    //首次點進章節時建立一筆新的進度
    @PostMapping("/chapter/{chapterId}")
    public ResponseEntity<?> createProgress(@PathVariable Integer chapterId,
                                            @RequestParam Long userId,
                                            @RequestParam Integer courseId) {
        Progress created = progressService.createProgress(userId, courseId, chapterId);
        return ResponseEntity.ok(created);
    }

    // 更新觀看進度 + 完成狀態
    @PatchMapping("/chapter/{chapterId}")
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
    @PatchMapping("/chapter/{chapterId}/complete")
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
    @GetMapping("/user/{userId}/course/{courseId}/completed")
    public ResponseEntity<?> checkCourseCompletion(@PathVariable Long userId, @PathVariable Integer courseId) {
        boolean completed = progressService.isCourseCompleted(userId, courseId);
        return ResponseEntity.ok(completed);
    }

}
