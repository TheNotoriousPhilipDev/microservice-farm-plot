package org.agomez.backend.msvc.farm.plot.services;

import org.agomez.backend.msvc.farm.plot.dto.Activity;
import org.agomez.backend.msvc.farm.plot.models.FarmPlot;

import java.util.List;
import java.util.Optional;

public interface FarmPlotService {

    List<FarmPlot> list();
    Optional<FarmPlot> findById(Long id);
    FarmPlot save(FarmPlot farmPlot);
    void deleteById(Long id);
    Optional<FarmPlot> byIdWithActivities(Long id);
    void deleteFarmPlotActivityByActivityId(Long activityId);

}
