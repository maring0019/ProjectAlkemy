package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.InvalidImageException;
import com.alkemy.ong.exception.InvalidUserException;
import com.alkemy.ong.model.ImageSlide;
import com.alkemy.ong.repository.ImageSlideRepository;
import com.alkemy.ong.service.Interface.IImgSlideService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImgSlideServiceImpl implements IImgSlideService {

    private final ImageSlideRepository imageRepo;
    private final MessageSource messageSource;

    public ImageSlide addImage(ImageSlide image) throws InvalidImageException, InvalidUserException {
        if (userIsAuthorized()) {
            if(doNotAdd(image))
                throw new InvalidImageException(messageSource.getMessage("slide.error.exists.img",null, Locale.getDefault()));
            ImageSlide newImage = new ImageSlide(
                    image.getImageUrl(), image.getText(), image.getOrdered(), image.getOrganizationId(), Date.from(Instant.now())
            );
            return imageRepo.save(newImage);
        } else {
            throw new InvalidUserException(messageSource.getMessage("slide.error.invalid.user", null, Locale.getDefault()));
        }
    }

    public List<ImageSlide> getAll() {
        return imageRepo.findAll().stream().sorted().collect(Collectors.toList());
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

}
