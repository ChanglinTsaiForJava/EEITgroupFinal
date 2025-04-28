package eeit.OldProject.yuni.Entity;

public enum Category {
    daily_care,
    dementia,
    nutrition,
    psychology,
    assistive,
    resource,
    endoflife,
    skills,
    testingforpostman,
    selfcare;


    public static boolean exists(String input) {
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}

