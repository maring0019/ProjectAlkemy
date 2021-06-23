package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.ActivitiesDto;

public interface ActivitiesService {

    ActivitiesDto createActivity(ActivitiesDto activitiesDto);

    ActivitiesDto updateActivity(Long id, ActivitiesDto activitiesDto);

}
