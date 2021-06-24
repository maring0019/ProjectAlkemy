package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivitiesDto;
import com.alkemy.ong.model.ActivitiesEntity;
import com.alkemy.ong.repository.ActivitiesRepository;
import com.alkemy.ong.service.Interface.IActivitiesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ActivitiesServiceImpl implements IActivitiesService {

    @Autowired
    ActivitiesRepository activitiesRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ActivitiesDto createActivity(ActivitiesDto activitiesDto) {

        ActivitiesEntity activitiesEntity = ActivitiesEntity.builder()
                .name(activitiesDto.getName())
                .content(activitiesDto.getContent())
                .image(activitiesDto.getImage())
                .created(new Date())
                .build();

        return mapper.map(activitiesRepository.save(activitiesEntity), ActivitiesDto.class);
    }

    @Override
    public ActivitiesDto updateActivity(Long id, ActivitiesDto activitiesDto) {
        ActivitiesEntity activitiesEntity = getActivityById(id);

        activitiesEntity.setName(activitiesDto.getName());
        activitiesEntity.setContent(activitiesEntity.getContent());
        activitiesEntity.setImage(activitiesEntity.getImage());
        activitiesEntity.setEdited(new Date());
        return mapper.map(activitiesRepository.save(activitiesEntity), ActivitiesDto.class);
    }

    @Override
    public List<ActivitiesDto> getAllActivities() {
        List<ActivitiesEntity> activitiesEntities = activitiesRepository.findAll();
        List<ActivitiesDto> activitiesDto = new ArrayList<>();
        activitiesEntities.forEach(a -> activitiesDto.add(mapper.map(a, ActivitiesDto.class)));
        return activitiesDto;
    }

    @Override
    public void deleteActivity(Long id) {
        ActivitiesEntity activitiesEntity = getActivityById(id);
        activitiesEntity.setDeletedAt(new Date());
        activitiesRepository.save(activitiesEntity);
        activitiesRepository.delete(activitiesEntity);
    }

    @Override
    public ActivitiesEntity getActivityById(Long id) {
        return activitiesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La actividad no existe."));
    }

}
