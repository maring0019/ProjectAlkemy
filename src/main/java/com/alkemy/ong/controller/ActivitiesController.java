package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivitiesDto;
import com.alkemy.ong.service.Interface.ActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/activities")
public class ActivitiesController {

    @Autowired
    ActivitiesService activitiesService;

    @PostMapping
    public ResponseEntity<ActivitiesDto> createActivity(@Valid @RequestBody ActivitiesDto activitiesDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activitiesService.createActivity(activitiesDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivitiesDto> updateActivity(@PathVariable Long id, @Valid @RequestBody ActivitiesDto activitiesDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(activitiesService.updateActivity(id, activitiesDto));
    }


}
