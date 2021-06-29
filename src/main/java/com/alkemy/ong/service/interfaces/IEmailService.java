package com.alkemy.ong.service.interfaces;

import com.sendgrid.helpers.mail.Mail;

import java.io.IOException;

public interface IEmailService {
    public void sendEmail(String email) throws IOException;
}
