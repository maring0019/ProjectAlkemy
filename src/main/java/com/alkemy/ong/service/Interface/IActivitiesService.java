package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.ActivitiesDto;
import com.alkemy.ong.model.ActivitiesEntity;

import java.util.List;

public interface IActivitiesService {

    ActivitiesDto createActivity(ActivitiesDto activitiesDto);

    ActivitiesDto updateActivity(Long id, ActivitiesDto activitiesDto);

    List<ActivitiesDto> getAllActivities();

    void deleteActivity(Long id);

    ActivitiesEntity getActivityById(Long id);

}
