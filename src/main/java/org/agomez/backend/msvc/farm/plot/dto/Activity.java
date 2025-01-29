package org.agomez.backend.msvc.farm.plot.dto;

import org.agomez.backend.msvc.farm.plot.utils.ActivityType;

import java.util.Date;
import java.util.List;

public class Activity {

    private Long id;

    private Date date;

    private String name;

    private double activityDuration;

    private ActivityType activityType;

    private List<AgronomicInputs> agronomicInputsList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(double activityDuration) {
        this.activityDuration = activityDuration;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public List<AgronomicInputs> getAgronomicInputsList() {
        return agronomicInputsList;
    }

    public void setAgronomicInputsList(List<AgronomicInputs> agronomicInputsList) {
        this.agronomicInputsList = agronomicInputsList;
    }

    public Activity() {
    }
}
