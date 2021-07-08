package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ImageSlideDto;
import com.alkemy.ong.exception.InvalidImageException;
import com.alkemy.ong.exception.InvalidUserException;
import com.alkemy.ong.model.ImageSlide;
import com.alkemy.ong.repository.ImageSlideRepository;
import com.alkemy.ong.service.Interface.IImgSlideService;
import lombok.AllArgsConstructor;

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
@AllArgsConstructor
public class ImgSlideServiceImpl implements IImgSlideService {

	@Autowired
    private final ImageSlideRepository imageRepo;
    private final MessageSource messageSource;

    @Autowired
    private ModelMapper mapper;
    
    public ImageSlide addImage(ImageSlide image) throws InvalidImageException, InvalidUserException {
        if (userIsAuthorized()) {
            if(doNotAdd(image))
                throw new InvalidImageException(messageSource.getMessage("slide.error.exists.img",null, Locale.getDefault()));
            ImageSlide newImage = new ImageSlide(
                    image.getImageUrl(), image.getText(), image.getOrdered(), image.getOrganization(), Date.from(Instant.now())
            );
            return imageRepo.save(newImage);
        } else {
            throw new InvalidUserException(messageSource.getMessage("slide.error.invalid.user", null, Locale.getDefault()));
        }
    }

    public List<ImageSlideDto> getAll() {
    	List<ImageSlide> slide = imageRepo.findAll().stream().sorted().collect(Collectors.toList());
    	List<ImageSlideDto> dto = new ArrayList<ImageSlideDto>();
    	
    	slide.forEach(sld -> dto.add(mapper.map(sld, ImageSlideDto.class)));
        return dto;
    }

    public ImageSlide updateImage(ImageSlide image) throws InvalidImageException {
        if(doNotAdd(image))
            throw new InvalidImageException(messageSource.getMessage("slide.error.exists.img",null, Locale.getDefault()));
        return imageRepo.save(image);
    }

    public void deleteImage(Long id){
        ImageSlide found = imageRepo.findById(id).orElseThrow(() -> new IllegalStateException(messageSource.getMessage("slide.error.do.not.exists",null, Locale.getDefault())));
        if(found != null)
            imageRepo.deleteById(id);
    }

    private boolean doNotAdd(ImageSlide image){
        return getAll().stream().anyMatch(savedImage -> savedImage.getImageUrl().equals(image.getImageUrl()));
    }


    private boolean userIsAuthorized(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return((UserDetails) principal).getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                        .equals("ROLE_ADMIN"));
    }

    @Override
    public ImageSlide getImageSlideById(Long id) {
        return imageRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(messageSource.getMessage("slide.error.do.not.exists", null, Locale.getDefault()))
        );
    }
}
