package eeit.OldProject.yuni.DTO;

public class UserCourseEnrollDto {
    private Long userId;
    private Integer courseId;


    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getCourseId() { return courseId; }

    public void setCourseId(Integer courseId) { this.courseId = courseId; }
}
