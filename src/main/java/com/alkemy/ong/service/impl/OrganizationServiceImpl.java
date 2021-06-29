package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.Interface.IOrganization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrganizationServiceImpl implements IOrganization {

    @Autowired
    private OrganizationRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OrganizationDto> getAll() {
        List<Organization> organizations = repository.findAll();
        List<OrganizationDto> organizationDto = new ArrayList<OrganizationDto>();

        organizations.forEach(org -> organizationDto.add(modelMapper.map(org, OrganizationDto.class)));
        return organizationDto;
    }
}
