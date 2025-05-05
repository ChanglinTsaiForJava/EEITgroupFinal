package eeit.OldProject.daniel.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostCategoryAssignBatchDto {
    private Long postId;
    private List<Long> categoryIds;
}