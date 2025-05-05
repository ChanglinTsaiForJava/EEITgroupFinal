package eeit.OldProject.daniel.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PostRequest {
    private String title;
    private String content;
    private Byte visibility;
    private Byte status;
    private Long userId;
    private List<Long> categoryIds;
    private List<Long> topicIds;
    private List<TagDto> tags;
}