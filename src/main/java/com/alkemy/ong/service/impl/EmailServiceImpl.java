package com.alkemy.ong.service.Impl;

import com.alkemy.ong.service.Interface.IEmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import com.alkemy.ong.util.EmailConstants;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;
    private final MessageSource messageSource;

    @Async
    public void send(String sendTo) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, EmailConstants.ENCODING );
            helper.setText(getEmailFromResources(), true);
            helper.setTo(sendTo);
            helper.setSubject(EmailConstants.SUCCESS);
            helper.setFrom(EmailConstants.SENT_FROM);
            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new IllegalStateException(messageSource.getMessage("email.error.cant.send", null, Locale.getDefault()));
        }
    }

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
