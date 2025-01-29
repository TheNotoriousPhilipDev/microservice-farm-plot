package org.agomez.backend.msvc.farm.plot.services;

import org.agomez.backend.msvc.farm.plot.dto.Activity;

import java.util.Optional;

public interface ActivityProxyService {

    Optional<Activity> assignActivityToPlotFarm(Long plotId, Activity activity);
    Optional<Activity> createActivityFromPlotFarmApi(Long plotId, Activity activity);
    Optional<Activity> removeActivityFromPlotFarm(Long plotId, Activity activity);


}
