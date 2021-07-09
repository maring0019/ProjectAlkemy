package com.alkemy.ong.service.Interface;

import com.alkemy.ong.dto.ContactsDto;
import org.springframework.stereotype.Service;

public interface IContacts {

    public ContactsDto createContacts(ContactsDto dto);
}
