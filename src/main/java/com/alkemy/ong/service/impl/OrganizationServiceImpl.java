package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationDtoComp;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.Interface.IOrganization;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements IOrganization {

    @Autowired
    private OrganizationRepository repository;
    
	@Autowired
	private final MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OrganizationDto> getAll() {
        List<Organization> organizations = repository.findAll();
        List<OrganizationDto> organizationDto = new ArrayList<OrganizationDto>();

        organizations.forEach(org -> organizationDto.add(modelMapper.map(org, OrganizationDto.class)));
        return organizationDto;
    }

	@Override
	public Organization getById(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(messageSource.getMessage("organization.error.registered", null, Locale.getDefault()))
				);
	}

	@Override
	public OrganizationDtoComp updateOrg(Long id, OrganizationDtoComp org) {
		Organization organization = getById(id);
		
		return modelMapper.map(repository.save(organization), OrganizationDtoComp.class);
	}


}
