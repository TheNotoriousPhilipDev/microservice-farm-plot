package org.agomez.backend.msvc.farm.plot.models;

import jakarta.persistence.*;

@Entity
@Table(name = "farm_plots_activities")
public class FarmPlotActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_id", unique = true)
    private Long activityId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FarmPlotActivity)) {
            return false;
        }
        FarmPlotActivity f = (FarmPlotActivity) obj;
        return this.activityId != null && this.activityId.equals(f.activityId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public FarmPlotActivity() {
    }
}
