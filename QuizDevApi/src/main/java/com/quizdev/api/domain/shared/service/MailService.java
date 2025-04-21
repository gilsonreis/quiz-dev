package com.quizdev.api.domain.shared.service;

import com.quizdev.api.domain.shared.service.dto.MailServiceInput;

public interface MailService {
    void send(MailServiceInput input);
}
