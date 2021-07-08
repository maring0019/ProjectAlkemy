package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivitiesDto;
import com.alkemy.ong.service.Interface.IActivities;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/activities")
@AllArgsConstructor
public class ActivitiesController {

    @Autowired
    private final IActivities activitiesService;

    @Autowired
    private final ModelMapper mapper;


    @GetMapping
    public ResponseEntity<List<ActivitiesDto>> getAllActivities() {
        return ResponseEntity.status(HttpStatus.OK).body(activitiesService.getAllActivities());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getActivityById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapper.map(activitiesService.getActivityById(id), ActivitiesDto.class));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ActivitiesDto> createActivity(@Valid @RequestBody ActivitiesDto activitiesDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activitiesService.createActivity(activitiesDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateActivity(@PathVariable Long id, @Valid @RequestBody ActivitiesDto activitiesDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(activitiesService.updateActivity(id, activitiesDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteActivityById(@PathVariable Long id) {
        try {
            activitiesService.deleteActivity(id);
            return ResponseEntity.status(HttpStatus.OK).body("Actividad eliminada satisfactoriamente.");
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(path = "{id}/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadActivityImage(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(activitiesService.uploadImage(id, file));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
