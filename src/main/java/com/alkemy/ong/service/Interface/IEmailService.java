package com.alkemy.ong.service.Interface;

import java.io.IOException;

public interface IEmailService {
    void send(String sendTo) throws IOException;
}
