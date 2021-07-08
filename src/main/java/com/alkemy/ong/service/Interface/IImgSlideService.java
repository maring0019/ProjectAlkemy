package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.ImageSlideDto;
import com.alkemy.ong.exception.InvalidImageException;
import com.alkemy.ong.exception.InvalidUserException;
import com.alkemy.ong.model.ImageSlide;

import java.util.List;

public interface IImgSlideService {
    ImageSlide addImage(ImageSlide image) throws InvalidImageException, InvalidUserException;
    List<ImageSlideDto> getAll();
    ImageSlide updateImage(ImageSlide image) throws InvalidImageException;
    void deleteImage(Long id);
}
