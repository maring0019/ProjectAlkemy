package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivitiesDto;
import com.alkemy.ong.model.ActivitiesEntity;
import com.alkemy.ong.repository.ActivitiesRepository;
import com.alkemy.ong.service.Interface.ActivitiesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActivitiesServiceImpl implements ActivitiesService {

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
        ActivitiesEntity activitiesEntity = activitiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La actividad no existe."));

        activitiesEntity.setName(activitiesDto.getName());
        activitiesEntity.setContent(activitiesEntity.getContent());
        activitiesEntity.setImage(activitiesEntity.getImage());
        activitiesEntity.setEdited(new Date());
        return mapper.map(activitiesRepository.save(activitiesEntity), ActivitiesDto.class);
    }

}
