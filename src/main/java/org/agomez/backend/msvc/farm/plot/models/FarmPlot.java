package org.agomez.backend.msvc.farm.plot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.agomez.backend.msvc.farm.plot.dto.Activity;
import org.agomez.backend.msvc.farm.plot.utils.CropType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "farm_plots")
public class FarmPlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private double latitude;

    @NotNull
    private double length;

    @Enumerated(EnumType.STRING)
    @Column(name = "crop_type", nullable = false)
    private CropType cropType;

    @Column(name = "hectare_size")
    private double hectareSize;

    @Transient
    private List<Activity> activities;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "farm_plot_id")
    private List<FarmPlotActivity> farmPlotActivities;

    public void addFarmPlotActivity(FarmPlotActivity farmPlotActivity) {
        this.farmPlotActivities.add(farmPlotActivity);
    }

    public void removeFarmPlotActivity(FarmPlotActivity farmPlotActivity) {
        this.farmPlotActivities.remove(farmPlotActivity);
    }

    public FarmPlot() {
        this.farmPlotActivities = new ArrayList<>();
        this.activities = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public double getHectareSize() {
        return hectareSize;
    }

    public void setHectareSize(double hectareSize) {
        this.hectareSize = hectareSize;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<FarmPlotActivity> getFarmPlotActivities() {
        return farmPlotActivities;
    }

    public void setFarmPlotActivities(List<FarmPlotActivity> farmPlotActivities) {
        this.farmPlotActivities = farmPlotActivities;
    }
}
