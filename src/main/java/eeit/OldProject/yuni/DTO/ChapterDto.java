package eeit.OldProject.yuni.DTO;

import eeit.OldProject.yuni.Entity.Chapter;
import lombok.Data;

@Data
public class ChapterDto {
    private Integer chapterId;
    private String title;
    private Integer position;
    private Integer courseId;

    public ChapterDto(Chapter chapter) {
        this.chapterId = chapter.getChapterId();
        this.title = chapter.getTitle();
        this.position = chapter.getPosition();
        this.courseId=chapter.getCourse().getCourseId();
    }
}
