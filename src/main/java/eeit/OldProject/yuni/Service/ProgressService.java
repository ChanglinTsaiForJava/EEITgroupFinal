package eeit.OldProject.yuni.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eeit.OldProject.steve.Entity.User;
import eeit.OldProject.yuni.Entity.Chapter;
import eeit.OldProject.yuni.Entity.Course;
import eeit.OldProject.yuni.Entity.Progress;
import eeit.OldProject.yuni.Entity.Status;
import eeit.OldProject.yuni.Repository.ProgressRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProgressService{
    @Autowired
    private ProgressRepository progressRepository;

// 查詢：使用者在某課程下所有章節的進度
    public List<Progress> getProgressByUserAndCourse(Long userId, Integer courseId) {
        return progressRepository.findByUserId_UserIdAndCourseId_CourseId(userId, courseId);
    }

// 查詢：使用者在某章節的單筆進度
public Optional<Progress> getProgressByUserAndChapter(Long userId, Integer chapterId) {
    return progressRepository.findByUserId_UserIdAndChapterId_ChapterId(userId, chapterId);
}

// 創建：首次進入章節時建立初始進度
public Progress createProgress(Long userId, Integer courseId, Integer chapterId) {
    Progress progress = new Progress();
    User user = new User();
    user.setUserId(userId);
    progress.setUserId(user);

    Course course = new Course();
    course.setCourseId(courseId);
    progress.setCourseId(course);

    Chapter chapter = new Chapter();
    chapter.setChapterId(chapterId);
    progress.setChapterId(chapter);

    progress.setStatus(Status.in_progress);
    progress.setIsCompleted(false);
    progress.setLastWatched(0f);
    progress.setCompletionDate(null);

    return progressRepository.save(progress);
}
// 更新章節進度（同時支援觀看進度跟完成狀態）
public Progress markChapterProgress(Progress progress, Float lastWatched, Boolean isCompleted) {
    if (lastWatched != null) {
        progress.setLastWatched(lastWatched);
    }
    if (isCompleted != null && isCompleted) {
        progress.setIsCompleted(true);
        progress.setStatus(Status.completed);
        progress.setCompletionDate(LocalDateTime.now());
    }
    return progressRepository.save(progress);
}

// 更新（只更新觀看進度
    public Progress updateLastWatchedOnly(Progress progress, Float lastWatched) {
        if (lastWatched != null) {
            progress.setLastWatched(lastWatched);
        }
        return progressRepository.save(progress);
    }

    // 手動標記章節完成
    public Progress completeChapter(Progress progress) {
        progress.setIsCompleted(true);
        progress.setStatus(Status.completed);
        progress.setCompletionDate(LocalDateTime.now());
        return progressRepository.save(progress);
    }


    // 計算已完成章節數
    public Integer countCompletedChapters(Long userId, Integer courseId) {
        return progressRepository.countByUserId_UserIdAndCourseId_CourseIdAndIsCompletedTrue(userId, courseId);
    }

    // 計算該課程總章節數
    public Integer countTotalChapters(Long userId, Integer courseId) {
        return progressRepository.countByUserId_UserIdAndCourseId_CourseId(userId, courseId);
    }

    // 判ㄉㄨㄢ使用者是否整門課完成（完成全部章節）
    public boolean isCourseCompleted(Long userId, Integer courseId) {
        Integer completed = countCompletedChapters(userId, courseId);
        Integer total = countTotalChapters(userId, courseId);
        return total != null && total > 0 && completed != null && completed.equals(total);
    }


}
