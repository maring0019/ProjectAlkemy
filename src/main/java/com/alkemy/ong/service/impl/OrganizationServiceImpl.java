package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationDtoComp;
import com.alkemy.ong.dto.SocialNetworkDto;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.SocialNetwork;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SocialNetworkRepository;
import com.alkemy.ong.service.Interface.IOrganization;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements IOrganization {

    @Autowired
    private OrganizationRepository repository;
    
    @Autowired
    private SocialNetworkRepository repoContact;
    
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
	
		organization.setName(org.getName());
		organization.setImage(org.getImage());
		organization.setAddress(org.getAddress());
		organization.setPhone(org.getPhone());
		organization.setWelcomeText(org.getWelcomeText());
		organization.setAboutUsText(org.getAboutUsText());
		organization.setEdited(new Date());
		

		return modelMapper.map(repository.save(organization), OrganizationDtoComp.class);
	}

	@Override
	public SocialNetworkDto newContact(Long id, SocialNetworkDto dto) {
		
		Organization org = this.getById(id);
			
		SocialNetwork contact = new SocialNetwork();

		if(!dto.getName().isBlank())
			contact.setName(dto.getName());
		if(!dto.getLink().isBlank())
			contact.setLink(dto.getLink());
		
		contact.setOrg(org);
		org.getContact().add(contact);

		
		repository.save(org);
			
		return modelMapper.map(repoContact.save(contact), SocialNetworkDto.class);
	}

	@Override
	public OrganizationDtoComp newOrg(OrganizationDtoComp dto) {
		
		Organization org = new Organization();
		org.setEmail(dto.getEmail());
		org.setName(dto.getName());
		org.setAboutUsText(dto.getAboutUsText());
		org.setAddress(dto.getAddress());
		org.setImage(dto.getImage());
		org.setPhone(dto.getPhone());
		org.setWelcomeText(dto.getWelcomeText());

		return modelMapper.map(repository.save(org), OrganizationDtoComp.class);
		
	}

}
