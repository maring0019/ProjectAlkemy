package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ImageSlideCreationDto;
import com.alkemy.ong.dto.ImageSlideDto;
import com.alkemy.ong.exception.InvalidImageException;
import com.alkemy.ong.model.ImageSlide;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.ImageSlideRepository;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.Interface.IFileStore;
import com.alkemy.ong.service.Interface.IImgSlideService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final OrganizationRepository organizationRepository;
    private final IFileStore fileStore;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Value("${aws.s3.bucket.endpointUrl}")
    private String bucketUrl;

    @Autowired
    public ImgSlideServiceImpl(ImageSlideRepository imageRepo, MessageSource messageSource, ModelMapper mapper, OrganizationRepository organizationRepository, IFileStore fileStore) {
        this.imageRepo = imageRepo;
        this.messageSource = messageSource;
        this.mapper = mapper;
        this.organizationRepository = organizationRepository;
        this.fileStore = fileStore;
    }



    @Override
    public ImageSlideDto createSlide(ImageSlideCreationDto imageSlideCreationDto) {

        Organization organization = getOrganizationById(imageSlideCreationDto.getOrganizationId());

        ImageSlide imageSlideEntity = new ImageSlide(
                imageSlideCreationDto.getText(),
                imageSlideCreationDto.getOrdered(),
                organization,
                Date.from(Instant.now())
        );

        ImageSlide imageSlideCreated = imageRepo.save(imageSlideEntity);
        uploadImage(imageSlideCreationDto, imageSlideCreated);
        return mapper.map(imageRepo.save(imageSlideCreated), ImageSlideDto.class);
    }


    @Override
    public List<ImageSlide> getAll() {
        return imageRepo.findAll().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public ImageSlideDto updateImage(Long id, ImageSlideCreationDto image) {
        ImageSlide imageSlide = getImageSlideById(id);
        imageSlide.setText(image.getText());
        imageSlide.setOrdered(imageSlide.getOrdered());
        uploadImage(image, imageSlide);
        ImageSlide imageSlideUpdated = imageRepo.save(imageSlide);
        return mapper.map(imageSlideUpdated, ImageSlideDto.class);
    }

    @Override
    public String deleteImage(Long id){
        ImageSlide imageSlide = getImageSlideById(id);
        imageRepo.delete(imageSlide);
        //Va el nombre del segundo parametro de cuando se crea, osea el nombre del folder --> String path = String.format("%s/%s", bucketName, "Image-Slide-" + imageSlide.getId() + "/");
        fileStore.deleteFilesFromS3Bucket("Image-Slide-" + imageSlide.getId()); //Le indicamos el nombre del folder
        return messageSource.getMessage("slide.delete.successful", null, Locale.getDefault());
    }

    @Override
    public List<ImageSlideCreationDto> getAllSlidesByOrganization(Long organizationId) {

        //Validamos que exista una organizacion con el id especificado
        Organization organization = getOrganizationById(organizationId);

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

    private Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                messageSource.getMessage("slide.error.organizationId.not.found", null, Locale.getDefault())
        ));
    }

    //Metodo privado de este service que consume el el servicio de amazon de subida de imagen
    private void uploadImage(ImageSlideCreationDto imageSlideCreationDto, ImageSlide imageSlide) {
        String path = String.format("%s/%s", bucketName, "Image-Slide-" + imageSlide.getId());
        String filename = String.format("%s-%s", imageSlideCreationDto.getImage().getOriginalFilename(), UUID.randomUUID());
        fileStore.save(path, filename, imageSlideCreationDto.getImage());
        String itemImageLink  = bucketUrl + "Image-Slide-" + imageSlide.getId() + "/" + filename;
        imageSlide.setImageUrl(itemImageLink);
    }


    private boolean userIsAuthorized(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return((UserDetails) principal).getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_ADMIN"));
    }


}
