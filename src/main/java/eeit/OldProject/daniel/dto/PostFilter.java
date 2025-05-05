package eeit.OldProject.daniel.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PostFilter {
    private Long    postId;
    private String  titleKeyword;
    private String  contentKeyword;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long    userId;
    private List<Long> postCategoryIds;
    private List<Long> postTopicIds;
    private List<Long> postTagIds;
    private Integer start;
    private Integer rows;
    private String sort;
    private String dir;
}
