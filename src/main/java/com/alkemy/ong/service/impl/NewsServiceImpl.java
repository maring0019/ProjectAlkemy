package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsCreationDto;
import com.alkemy.ong.dto.NewsResponseDto;
import com.alkemy.ong.model.Categories;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.Interface.ICategories;
import com.alkemy.ong.service.Interface.IFileStore;
import com.alkemy.ong.service.Interface.INews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;
import java.util.Date;


import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Locale;
import java.util.UUID;


@Service
public class NewsServiceImpl implements INews {

    private final NewsRepository newsRepository;
    private final ProjectionFactory projectionFactory;
    private final MessageSource messageSource;
    private final IFileStore fileStore;
    private final ICategories categoriesService;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Value("${aws.s3.bucket.endpointUrl}")
    private String bucketUrl;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, ProjectionFactory projectionFactory, MessageSource messageSource, IFileStore fileStore, ICategories categoriesService) {
        this.newsRepository = newsRepository;
        this.projectionFactory = projectionFactory;
        this.messageSource = messageSource;
        this.fileStore = fileStore;
        this.categoriesService = categoriesService;
    }


    public News getNewById(Long id) {
        return newsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        messageSource.getMessage("new.error.not.found", null, Locale.getDefault())
                )
        );
    }

    @Override
    public List<News> findAll() {
        return null;
    }

    @Override
    public NewsResponseDto save(NewsCreationDto newsCreationDto) {

        Categories categoriesEntity = categoriesService.findCategoriesById(newsCreationDto.getCategory());

        News newsEntity = News.builder()
                .name(newsCreationDto.getName())
                .content(newsCreationDto.getContent())
                .category(categoriesEntity)
                .build();
        News newsCreated = newsRepository.save(newsEntity);
        uploadImage(newsCreationDto.getImage(), newsCreated);
        return projectionFactory.createProjection(NewsResponseDto.class, newsRepository.save(newsCreated));
    }

    @Override
    public void deleteNews(Long id) {
        News newsEntity = getNewById(id);
        newsRepository.delete(newsEntity);
        fileStore.deleteFilesFromS3Bucket("News-" + newsEntity.getId());
    }

    @Override
    public NewsResponseDto updateNews(Long id, NewsCreationDto newsCreationDto) {

        News newsUpdated = getNewById(id);
        Categories categoriesEntity = categoriesService.findCategoriesById(newsCreationDto.getCategory());

        newsUpdated.setCategory(categoriesEntity);
        newsUpdated.setContent(newsCreationDto.getContent());
        newsUpdated.setName(newsCreationDto.getName());
        if(newsCreationDto.getImage() != null) {
            uploadImage(newsCreationDto.getImage(), newsUpdated);
        }
        newsUpdated.setEdited(new Date());
        return projectionFactory.createProjection(NewsResponseDto.class, newsRepository.save(newsUpdated));
    }

    @Override
    public Page<NewsResponseDto> getAllNewsPaginated(int page, int limit, String sortBy, String sortDir) {
        if (page > 0) {
            page = page - 1;
        }

        Pageable pageable = PageRequest.of(
                page, limit,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        return newsRepository.findAllProjectedBy(pageable);
    }


    private void uploadImage(MultipartFile imageFile, News news) {
        String path = String.format("%s/%s", bucketName, "News-" + news.getId());
        String filename = String.format("%s-%s", imageFile.getOriginalFilename(), UUID.randomUUID());
        fileStore.save(path, filename, imageFile);
        String itemImageLink  = bucketUrl + "News-" + news.getId() + "/" + filename;
        news.setImage(itemImageLink);
    }
}
