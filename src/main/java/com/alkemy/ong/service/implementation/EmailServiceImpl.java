package com.alkemy.ong.service.implementation;

import com.alkemy.ong.service.interfaces.IEmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements IEmailService {

    @Value("${app.sendgrid.templateId}")
    private String templateId;

    @Value("${app.sendgrid.email}")
    private String emailFrom;

    @Value("${app.sendgrid.key}")
    private String appkey;

    @Override
    public void sendEmail(String email) throws IOException {
        Email from = new Email(emailFrom);
        Email to = new Email(email);
        String subject = "subject mensaje";
        Content content = new Content("text/plain", "mensaje de prueba");
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(appkey);
        Request request = new Request();

        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        }catch (IOException ex){
            throw ex;
        }
    }

}
