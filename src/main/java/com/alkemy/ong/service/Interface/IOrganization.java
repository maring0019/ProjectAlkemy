package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationDtoComp;
import com.alkemy.ong.model.Organization;

import java.util.List;

public interface IOrganization {

    List<OrganizationDto> getAll();
    
    Organization getById(Long id);
    
    OrganizationDtoComp updateOrg(Long id, OrganizationDtoComp org);

}
