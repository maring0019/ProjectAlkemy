package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ImageSlideCreationDto;
import com.alkemy.ong.dto.ImageSlideDto;
import com.alkemy.ong.model.ImageSlide;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.ImageSlideRepository;
import com.alkemy.ong.service.Interface.IFileStore;
import com.alkemy.ong.service.Interface.IImgSlideService;
import com.alkemy.ong.service.Interface.IOrganization;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImgSlideServiceImpl implements IImgSlideService {


    private final ImageSlideRepository imageRepo;

    private final MessageSource messageSource;

    private final ModelMapper mapper;

    private final IOrganization organizationService;

    private final IFileStore fileStore;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Value("${aws.s3.bucket.endpointUrl}")
    private String bucketUrl;

    @Autowired
    public ImgSlideServiceImpl(ImageSlideRepository imageRepo, MessageSource messageSource, ModelMapper mapper, IOrganization organizationService, IFileStore fileStore) {
        this.imageRepo = imageRepo;
        this.messageSource = messageSource;
        this.mapper = mapper;
        this.organizationService = organizationService;
        this.fileStore = fileStore;
    }


    @Override
    public ImageSlideDto createSlide(ImageSlideCreationDto imageSlideCreationDto) {

        Organization organization = organizationService.getById(imageSlideCreationDto.getOrganizationId());

        ImageSlide imageSlideEntity = new ImageSlide(
                imageSlideCreationDto.getText(),
                imageSlideCreationDto.getOrdered(),
                organization,
                Date.from(Instant.now())
        );

        ImageSlide imageSlideCreated = imageRepo.save(imageSlideEntity);
        imageSlideCreated.setImageUrl(fileStore.save(imageSlideCreated, imageSlideCreationDto.getImage()));
        return mapper.map(imageRepo.save(imageSlideCreated), ImageSlideDto.class);
    }


    @Override
    public List<ImageSlide> getAll() {
        return imageRepo.findAll().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public ImageSlideDto updateImage(Long id, ImageSlideCreationDto image) {
        ImageSlide imageSlide = getImageSlideById(id);
        if(image.getText()!=null)
            imageSlide.setText(image.getText());
        if(image.getOrdered()!=null)
            imageSlide.setOrdered(image.getOrdered());

        imageSlide.setImageUrl(fileStore.save(imageSlide, image.getImage()));
        return mapper.map(imageRepo.save(imageSlide), ImageSlideDto.class);
    }

    @Override
    public String deleteImage(Long id){
        ImageSlide imageSlide = getImageSlideById(id);
        imageRepo.delete(imageSlide);
        fileStore.deleteFilesFromS3Bucket(imageSlide);
        return messageSource.getMessage("slide.delete.successful", null, Locale.getDefault());
    }

    @Override
    public List<ImageSlideCreationDto> getAllSlidesByOrganization(Long organizationId) {

        //Validamos que exista una organizacion con el id especificado
        Organization organization = organizationService.getById(organizationId);

        List<ImageSlide> imageSlides = imageRepo.findAllByOrganizationOrderByOrdered(organization);
        List<ImageSlideCreationDto> imageSlideCreationDtos = new ArrayList<>();
        imageSlides.forEach(i -> imageSlideCreationDtos.add(mapper.map(i, ImageSlideCreationDto.class)));
        return imageSlideCreationDtos;
    }

    @Override
    public ImageSlide getImageSlideById(Long id) {
        return imageRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                messageSource.getMessage("slide.error.not.found",null, Locale.getDefault())
        ));
    }

}
