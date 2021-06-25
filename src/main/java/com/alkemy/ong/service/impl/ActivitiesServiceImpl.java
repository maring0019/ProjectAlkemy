package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivitiesDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivitiesRepository;
import com.alkemy.ong.service.Interface.IActivities;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ActivitiesServiceImpl implements IActivities {

    @Autowired
    private final ActivitiesRepository activitiesRepository;

    @Autowired
    private final ModelMapper mapper;

    @Autowired
    private final MessageSource messageSource;

    @Override
    public ActivitiesDto createActivity(ActivitiesDto activitiesDto) {

        Activity activity = Activity.builder()
                .name(activitiesDto.getName())
                .content(activitiesDto.getContent())
                .image(activitiesDto.getImage())
                .created(new Date())
                .build();

        return mapper.map(activitiesRepository.save(activity), ActivitiesDto.class);
    }

    @Override
    public ActivitiesDto updateActivity(Long id, ActivitiesDto activitiesDto) {
        Activity activity = getActivityById(id);

        activity.setName(activitiesDto.getName());
        activity.setContent(activitiesDto.getContent());
        activity.setImage(activitiesDto.getImage());
        activity.setEdited(new Date());
        return mapper.map(activitiesRepository.save(activity), ActivitiesDto.class);
    }

    @Override
    public List<ActivitiesDto> getAllActivities() {
        List<Activity> activityEntities = activitiesRepository.findAll();
        List<ActivitiesDto> activitiesDto = new ArrayList<>();
        activityEntities.forEach(a -> activitiesDto.add(mapper.map(a, ActivitiesDto.class)));
        return activitiesDto;
    }

    @Override
    public void deleteActivity(Long id) {
        Activity activity = getActivityById(id);
        activity.setDeletedAt(new Date());
        activitiesRepository.save(activity);
        activitiesRepository.delete(activity);
    }

    @Override
    public Activity getActivityById(Long id) {
        return activitiesRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        messageSource.getMessage("activity.error.not.found", null, Locale.getDefault())
                )
        );
    }

}
