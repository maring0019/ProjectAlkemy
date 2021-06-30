package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivitiesDto;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivitiesRepository;
import com.alkemy.ong.service.Interface.IActivities;
import com.alkemy.ong.service.Interface.IFileStore;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class ActivitiesServiceImpl implements IActivities {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.bucket.endpointUrl}")
    private String bucketUrl;

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

    @Override
    public String uploadImage(Long id, MultipartFile file) {
        //1. Chequear si la imagen no esta vacia
        fileStore.fileIsEmpty(file);
        //2. Si el archivo NO es una imagen valida
        fileStore.isAnImage(file);
        //3. Chequear si la actividad existe en la DB
        Activity activity = getActivityById(id);
        //4. Grabar metadata del archivo
        Map<String, String> metadata = fileStore.extractMetadata(file);

        //5. Almacenar la imagen en s3 y actualizar la db (imagen from Activity) con el link de la imagen en s3
        //El campo imagen sera: el nombre del actual bucket/el id de la actividad-nombre-de-la-actividad    <-- Asi quedara formado el directorio/folder del file
        String path = String.format("%s/%s", bucketName, activity.getId() + "-" + activity.getName().replaceAll("\\s", "-"));
        //El nombre de la imagen quedara formado por el nombre original-UUID random
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            activity.setImage(path+filename); //Seteo el nuevo link de la imagen
            activitiesRepository.save(activity);
            return bucketUrl + path + filename;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


}
