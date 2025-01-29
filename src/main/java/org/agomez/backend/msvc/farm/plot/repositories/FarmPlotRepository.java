package org.agomez.backend.msvc.farm.plot.repositories;

import org.agomez.backend.msvc.farm.plot.models.FarmPlot;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FarmPlotRepository extends CrudRepository<FarmPlot, Long> {

    @Modifying
    @Query("DELETE FROM FarmPlotActivity f WHERE f.activityId = ?1")
    void deleteFarmPlotActivityByActivityId(Long activityId);
}
