package com.alkemy.ong.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alkemy.ong.service.impl.UsersServiceImpl;

@Configuration
public class AppConfiguration {

    //Para tener una instancia ModelMapper global para no estar instanciando cada rato
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        return mapper;
    }

    @Bean
    public UsersServiceImpl userServiceImpl() {
        UsersServiceImpl userServiceImpl = new UsersServiceImpl();
        return userServiceImpl;
    }

}