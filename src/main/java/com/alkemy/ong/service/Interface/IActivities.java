package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.ActivitiesDto;
import com.alkemy.ong.model.Activity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IActivities {

    ActivitiesDto createActivity(ActivitiesDto activitiesDto);

    ActivitiesDto updateActivity(Long id, ActivitiesDto activitiesDto);

    List<ActivitiesDto> getAllActivities();

    void deleteActivity(Long id);

    Activity getActivityById(Long id);

    String uploadImage(Long id, MultipartFile file);

}
