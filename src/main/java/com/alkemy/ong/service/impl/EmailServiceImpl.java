package com.alkemy.ong.service.Impl;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import com.alkemy.ong.util.EmailConstants;

@Service
@AllArgsConstructor
public class EmailServiceImpl{

    private final MessageSource messageSource;

    private String getEmailFromResources() throws IOException {
        String email = "";
        String line;
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            InputStream inputStream = classLoader.getResourceAsStream(EmailConstants.EMAIL_TEMPLATE);
            assert inputStream != null;
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            while ((line = reader.readLine()) != null) {
                email = email.concat(line);
            }
        } catch (IOException e) {
           throw new IOException(messageSource.getMessage("email.error.cant.get.email", null, Locale.getDefault()));
        }
        return email;

    }

}
