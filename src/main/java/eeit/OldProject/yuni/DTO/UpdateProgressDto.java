package eeit.OldProject.yuni.DTO;

import lombok.Data;
import eeit.OldProject.yuni.Entity.Status;


public class UpdateProgressDto {
    private Long userId;
    private Float lastWatched;
    private Boolean isCompleted;
    private Status status;

    public Long getUserId() {
        return userId;
    }

    public Float getLastWatched() {
        return lastWatched;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }



    public Status getStatus() {
        return status;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLastWatched(Float lastWatched) {
        this.lastWatched = lastWatched;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}