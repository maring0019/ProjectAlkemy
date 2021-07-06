package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ImageSlideDto;
import com.alkemy.ong.exception.InvalidImageException;
import com.alkemy.ong.exception.InvalidUserException;
import com.alkemy.ong.model.ImageSlide;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.ImageSlideRepository;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.Interface.IImgSlideService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@Service
public class ImgSlideServiceImpl implements IImgSlideService {

    private final ImageSlideRepository imageRepo;
    private final MessageSource messageSource;
    private final ModelMapper mapper;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public ImgSlideServiceImpl(ImageSlideRepository imageRepo, MessageSource messageSource, ModelMapper mapper, OrganizationRepository organizationRepository) {
        this.imageRepo = imageRepo;
        this.messageSource = messageSource;
        this.mapper = mapper;
        this.organizationRepository = organizationRepository;
    }


    @Override
    public ImageSlideDto createSlide(ImageSlideDto imageSlideDto) {
        ImageSlide imageSlideCreated = new ImageSlide(
                imageSlideDto.getImageUrl(),
                imageSlideDto.getText(),
                imageSlideDto.getOrdered(),
                imageSlideDto.getOrganizationId(),
                Date.from(Instant.now())
        );
        return mapper.map(imageRepo.save(imageSlideCreated), ImageSlideDto.class);
    }


    public ImageSlideDto addImage(ImageSlideDto image) throws InvalidImageException, InvalidUserException {
        if (userIsAuthorized()) {
            if(doNotAdd(image))
                throw new InvalidImageException(messageSource.getMessage("slide.error.exists.img",null, Locale.getDefault()));
            ImageSlide newImage = new ImageSlide(
                    image.getImageUrl(), image.getText(), image.getOrdered(), image.getOrganizationId(), Date.from(Instant.now())
            );
            return mapper.map(imageRepo.save(newImage), ImageSlideDto.class);
        } else {
            throw new InvalidUserException(messageSource.getMessage("slide.error.invalid.user", null, Locale.getDefault()));
        }
    }



    public List<ImageSlide> getAll() {
        return imageRepo.findAll().stream().sorted().collect(Collectors.toList());
    }

    public ImageSlideDto updateImage(Long id, ImageSlideDto image) throws InvalidImageException {
        if(doNotAdd(image))
            throw new InvalidImageException(messageSource.getMessage("slide.error.exists.img",null, Locale.getDefault()));

        ImageSlide imageSlide = getImageSlideById(id);
        imageSlide.setOrdered(image.getOrdered());
        imageSlide.setText(image.getText());
        imageSlide.setImageUrl(image.getImageUrl());
        imageSlide.setOrganizationId(image.getOrganizationId());
        return mapper.map(imageRepo.save(imageSlide), ImageSlideDto.class);
    }

    public void deleteImage(Long id){
        ImageSlide found = imageRepo.findById(id).orElseThrow(() -> new IllegalStateException(messageSource.getMessage("slide.error.do.not.exists",null, Locale.getDefault())));
        if(found != null)
            imageRepo.deleteById(id);
    }

    @Override
    public List<ImageSlideDto> getAllSlidesByOrganization(Long organizationId) {

        //Validamos que exista una organizacion con el id especificado
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new EntityNotFoundException(
                messageSource.getMessage("slide.error.organizationId.not.found", null, Locale.getDefault())
        ));

        List<ImageSlide> imageSlides = imageRepo.findAllByOrganizationIdOrderByOrdered(organization.getId());
        List<ImageSlideDto> imageSlideDtos = new ArrayList<>();
        imageSlides.forEach(i -> imageSlideDtos.add(mapper.map(i, ImageSlideDto.class)));
        return imageSlideDtos;
    }

    @Override
    public ImageSlide getImageSlideById(Long id) {
        return imageRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                messageSource.getMessage("slide.error.do.not.exists",null, Locale.getDefault())
        ));
    }

    private boolean doNotAdd(ImageSlideDto image){
        return getAll().stream().anyMatch(savedImage -> savedImage.getImageUrl().equals(image.getImageUrl()));
    }


    private boolean userIsAuthorized(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return((UserDetails) principal).getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_ADMIN"));
    }

}
