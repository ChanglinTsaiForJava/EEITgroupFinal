package eeit.OldProject.yuni.DTO;

import lombok.Data;

@Data
public class PrevNextChapterDto {
    private ChapterDto prev;
    private ChapterDto next;

    public PrevNextChapterDto(ChapterDto prev, ChapterDto next) {
        this.prev = prev;
        this.next = next;
    }
}
