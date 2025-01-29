package org.agomez.backend.msvc.farm.plot.clients;

import org.agomez.backend.msvc.farm.plot.dto.Activity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-agronomic-activities", url = "http://localhost:8004")
public interface ActivityRestClient {

    @GetMapping("/api/v1/activities")
    Activity getActivities();

    @GetMapping("/api/v1/activities/{id}")
    Activity getActivity(@PathVariable Long id);

    @PostMapping("/api/v1/activities")
    Activity createActivity(@RequestBody Activity activity);

    @GetMapping("/api/v1/activities/by-farm-plot")
    List<Activity> getActivitiesByFarmPlots(@RequestParam Iterable<Long> ids);

}
