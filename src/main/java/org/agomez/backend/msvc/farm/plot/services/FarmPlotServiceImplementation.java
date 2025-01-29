package org.agomez.backend.msvc.farm.plot.services;

import org.agomez.backend.msvc.farm.plot.clients.ActivityRestClient;
import org.agomez.backend.msvc.farm.plot.dto.Activity;
import org.agomez.backend.msvc.farm.plot.models.FarmPlot;
import org.agomez.backend.msvc.farm.plot.models.FarmPlotActivity;
import org.agomez.backend.msvc.farm.plot.repositories.FarmPlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmPlotServiceImplementation implements FarmPlotService, ActivityProxyService {

    private final FarmPlotRepository farmPlotRepository;

    private final ActivityRestClient activityRestClient;

    public FarmPlotServiceImplementation(FarmPlotRepository farmPlotRepository,
                                         ActivityRestClient activityRestClient) {
        this.farmPlotRepository = farmPlotRepository;
        this.activityRestClient = activityRestClient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmPlot> list() {
        return (List<FarmPlot>) farmPlotRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FarmPlot> findById(Long id) {
        return farmPlotRepository.findById(id);
    }

    @Override
    @Transactional
    public FarmPlot save(FarmPlot farmPlot) {
        return farmPlotRepository.save(farmPlot);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        farmPlotRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FarmPlot> byIdWithActivities(Long id) {
        Optional<FarmPlot> f = farmPlotRepository.findById(id);
        if (f.isPresent()){
            FarmPlot farmPlot = f.get();
            if (!farmPlot.getFarmPlotActivities().isEmpty()){
                List<Long> ids = farmPlot.getFarmPlotActivities()
                        .stream()
                        .map(FarmPlotActivity::getActivityId)
                        .toList();
                List<Activity> activities = activityRestClient.getActivitiesByFarmPlots(ids);
                farmPlot.setActivities(activities);
            }
            return Optional.of(farmPlot);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteFarmPlotActivityByActivityId(Long activityId) {
        farmPlotRepository.deleteFarmPlotActivityByActivityId(activityId);
    }

    @Override
    @Transactional
    public Optional<Activity> assignActivityToPlotFarm(Long plotId, Activity activity) {
        Optional<FarmPlot> optionalFarmPlot = farmPlotRepository.findById(plotId);
        if (optionalFarmPlot.isPresent()) {
            Activity activityFromMsvc = activityRestClient.getActivity(activity.getId());
            FarmPlot farmPlot = optionalFarmPlot.get();
            FarmPlotActivity farmPlotActivity = new FarmPlotActivity();
            farmPlotActivity.setActivityId(activityFromMsvc.getId());

            farmPlot.addFarmPlotActivity(farmPlotActivity);
            farmPlotRepository.save(farmPlot);
            return Optional.of(activityFromMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Activity> createActivityFromPlotFarmApi(Long plotId, Activity activity) {
        Optional<FarmPlot> optionalFarmPlot = farmPlotRepository.findById(plotId);
        if (optionalFarmPlot.isPresent()) {
            Activity activityNewMsvc = activityRestClient.createActivity(activity);
            FarmPlot farmPlot = optionalFarmPlot.get();
            FarmPlotActivity farmPlotActivity = new FarmPlotActivity();
            farmPlotActivity.setActivityId(activityNewMsvc.getId());

            farmPlot.addFarmPlotActivity(farmPlotActivity);
            farmPlotRepository.save(farmPlot);
            return Optional.of(activityNewMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Activity> removeActivityFromPlotFarm(Long plotId, Activity activity) {
        Optional<FarmPlot> optionalFarmPlot = farmPlotRepository.findById(plotId);
        if (optionalFarmPlot.isPresent()) {
            Activity activityFromMsvc = activityRestClient.getActivity(activity.getId());
            FarmPlot farmPlot = optionalFarmPlot.get();
            FarmPlotActivity farmPlotActivity = new FarmPlotActivity();
            farmPlotActivity.setActivityId(activityFromMsvc.getId());

            farmPlot.removeFarmPlotActivity(farmPlotActivity);
            farmPlotRepository.save(farmPlot);
            return Optional.of(activityFromMsvc);
        }

        return Optional.empty();
    }
}
