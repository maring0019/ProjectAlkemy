package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivitiesDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivitiesRepository;
import com.alkemy.ong.service.Interface.IActivities;
import com.alkemy.ong.service.Interface.IFileStore;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class ActivitiesServiceImpl implements IActivities {

    private final ActivitiesRepository activitiesRepository;
    private final IFileStore fileStore;
    private final ModelMapper mapper;
    private final MessageSource messageSource;

    @Autowired
    public ActivitiesServiceImpl(ActivitiesRepository activitiesRepository, IFileStore fileStore, ModelMapper mapper, MessageSource messageSource) {
        this.activitiesRepository = activitiesRepository;
        this.fileStore = fileStore;
        this.mapper = mapper;
        this.messageSource = messageSource;
    }

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
    public ActivitiesDto updateActivity(Long id, ActivitiesDto dto) {
        Activity activity = getActivityById(id);

        if(!dto.getName().isBlank())
        	activity.setName(dto.getName());
        if(!dto.getContent().isBlank())
        	activity.setContent(dto.getContent());
        if(!dto.getImage().isBlank())
        	activity.setImage(dto.getImage());
        
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
        //Para eliminar las imagenes almacenadas en el s3 bucket le debo pasar el nombre del directorio donde estan las n imagenes
        //No es que tiene n images, sino que al poner otra imagen esta se almacena en el mismo directorio por ende al eliminar la actividad eliminamos todas las imagenes que tuvo
        fileStore.deleteFilesFromS3Bucket(activity.getId() + "-" + activity.getName().replaceAll("\\s", "-"));
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
