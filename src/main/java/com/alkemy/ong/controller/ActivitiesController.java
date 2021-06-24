package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivitiesDto;
import com.alkemy.ong.service.impl.ActivitiesServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/activities")
@AllArgsConstructor
public class ActivitiesController {

    @Autowired
    private ActivitiesServiceImpl activitiesService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ActivitiesDto>> getAllActivities() {
        return ResponseEntity.status(HttpStatus.OK).body(activitiesService.getAllActivities());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ActivitiesDto> getActivityById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.map(activitiesService.getActivityById(id), ActivitiesDto.class));
    }

    @PostMapping
    public ResponseEntity<ActivitiesDto> createActivity(@Valid @RequestBody ActivitiesDto activitiesDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activitiesService.createActivity(activitiesDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivitiesDto> updateActivity(@PathVariable Long id, @Valid @RequestBody ActivitiesDto activitiesDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(activitiesService.updateActivity(id, activitiesDto));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteActivityById(@PathVariable Long id) {
        activitiesService.deleteActivity(id);
        return ResponseEntity.status(HttpStatus.OK).body("Actividad eliminada correctamente.");
    }

}
