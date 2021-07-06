package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.ImageSlideDto;
import com.alkemy.ong.exception.InvalidImageException;
import com.alkemy.ong.exception.InvalidUserException;
import com.alkemy.ong.model.ImageSlide;

import java.util.List;

public interface IImgSlideService {
    ImageSlideDto addImage(ImageSlideDto image) throws InvalidImageException, InvalidUserException;
    ImageSlideDto createSlide(ImageSlideDto imageSlideDto);
    List<ImageSlide> getAll();
    ImageSlideDto updateImage(Long id, ImageSlideDto image) throws InvalidImageException;
    void deleteImage(Long id);
    List<ImageSlideDto> getAllSlidesByOrganization(Long organizationId);
    ImageSlide getImageSlideById(Long id);
}
