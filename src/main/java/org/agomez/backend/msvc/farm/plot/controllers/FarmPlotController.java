package org.agomez.backend.msvc.farm.plot.controllers;

import feign.FeignException;
import org.agomez.backend.msvc.farm.plot.dto.Activity;
import org.agomez.backend.msvc.farm.plot.models.FarmPlot;
import org.agomez.backend.msvc.farm.plot.services.ActivityProxyService;
import org.agomez.backend.msvc.farm.plot.services.FarmPlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/farm-plots")
@RestController
public class FarmPlotController {

    private final FarmPlotService farmPlotService;
    private final ActivityProxyService activityProxyService;

    public FarmPlotController(FarmPlotService farmPlotService, ActivityProxyService activityProxyService) {
        this.farmPlotService = farmPlotService;
        this.activityProxyService = activityProxyService;
    }

    @GetMapping
    public List<FarmPlot> list() {
        return farmPlotService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFarmPlot(@PathVariable Long id) {
        Optional<FarmPlot> optionalFarmPlot = farmPlotService.byIdWithActivities(id);
        if (optionalFarmPlot.isPresent()) {
            return ResponseEntity.ok(optionalFarmPlot.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createFarmPlot(@RequestBody FarmPlot farmPlot) {
        return ResponseEntity.status(HttpStatus.CREATED).body(farmPlotService.save(farmPlot));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFarmPlot(@PathVariable Long id, @RequestBody FarmPlot farmPlot) {
        Optional<FarmPlot> optionalFarmPlot = farmPlotService.findById(id);
        if (optionalFarmPlot.isPresent()) {
            FarmPlot farmPlotToUpdate = optionalFarmPlot.get();
            farmPlotToUpdate.setName(farmPlot.getName());
            farmPlotToUpdate.setLatitude(farmPlot.getLatitude());
            farmPlotToUpdate.setLength(farmPlot.getLength());
            farmPlotToUpdate.setCropType(farmPlot.getCropType());
            farmPlotToUpdate.setHectareSize(farmPlot.getHectareSize());
            farmPlotToUpdate.setFarmPlotActivities(farmPlot.getFarmPlotActivities());
            return ResponseEntity.status(HttpStatus.CREATED).body(farmPlotService.save(farmPlotToUpdate));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmPlot(@PathVariable Long id) {
        Optional<FarmPlot> f = farmPlotService.findById(id);
        if (f.isPresent()) {
            farmPlotService.deleteById(f.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assign-activity/{plotId}")
    public ResponseEntity<?> assignActivityToPlotFarm(@PathVariable Long plotId, @RequestBody Activity activity) {
        //id field does go in the body
        Optional<Activity> optionalActivity;
        try {
            optionalActivity = activityProxyService.assignActivityToPlotFarm(plotId, activity);
        } catch (FeignException e) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Message", "Activity not found: " + e.getMessage()));
        }if (optionalActivity.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalActivity.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-activity/{plotId}")
    public ResponseEntity<?> createActivityToPlotFarm(@PathVariable Long plotId, @RequestBody Activity activity) {
        // do not pass id field in JSON calling when creating user because it is auto-generated
        Optional<Activity> optionalActivity;
        try {
            optionalActivity = activityProxyService.createActivityFromPlotFarmApi(plotId, activity);
        } catch (FeignException e) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Message", "Activity not created: " + e.getMessage()));
        }if (optionalActivity.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalActivity.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-activity/{plotId}")
    public ResponseEntity<?> deleteActivityFromPlotFarm(@PathVariable Long plotId, @RequestBody Activity activity) {
        //id field does go in the body
        Optional<Activity> optionalActivity;
        try {
            optionalActivity = activityProxyService.removeActivityFromPlotFarm(plotId, activity);
        } catch (FeignException e) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Message", "Activity not found: " + e.getMessage()));
        }if (optionalActivity.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalActivity.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-activity/{id}")
    public ResponseEntity<?> deleteFarmPlotActivity(@PathVariable Long id) {
        farmPlotService.deleteFarmPlotActivityByActivityId(id);
        return ResponseEntity.noContent().build();
    }



}
