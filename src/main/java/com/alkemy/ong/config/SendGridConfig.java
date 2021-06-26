package com.alkemy.ong.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

    /*@Value("${app.sendgrid.key}")
    private String appkey;

    @Bean
    public SendGrid getSengrid(){
        return new SendGrid(appkey);
    }*/

}
