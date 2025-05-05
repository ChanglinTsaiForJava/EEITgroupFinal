package eeit.OldProject.yuni.DTO;

import eeit.OldProject.yuni.Entity.Chapter;
import eeit.OldProject.yuni.Entity.ContentType;
import lombok.Data;

@Data
public class ChapterDto {
    private Integer chapterId;
    private String title;
    private Integer position;
    private Integer courseId;
    private ContentType contentType;
    private String contentUrl;

    public ChapterDto(Chapter chapter) {
        this.chapterId = chapter.getChapterId();
        this.title = chapter.getTitle();
        this.position = chapter.getPosition();
        this.courseId = chapter.getCourse().getCourseId();
        this.contentType = chapter.getContentType();
        this.contentUrl = chapter.getContentUrl();
    }
}
