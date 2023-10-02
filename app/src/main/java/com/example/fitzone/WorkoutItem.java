package com.example.fitzone;

public class WorkoutItem {
    private String workoutName;
    private String focusArea;
    private int workoutImageResourceId;

    public WorkoutItem(String workoutName, String focusArea, int workoutImageResourceId) {
        this.workoutName = workoutName;
        this.focusArea = focusArea;
        this.workoutImageResourceId = workoutImageResourceId;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getFocusArea() {
        return focusArea;
    }

    public int getWorkoutImageResourceId() {
        return workoutImageResourceId;
    }
}
