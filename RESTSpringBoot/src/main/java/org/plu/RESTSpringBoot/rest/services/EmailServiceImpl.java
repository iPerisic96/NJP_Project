package org.plu.RESTSpringBoot.rest.services;

import org.plu.RESTSpringBoot.entities.ActivationLink;
import org.plu.RESTSpringBoot.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public boolean confirmActivation(String tok) {
        return false;
    }

    @Override
    public ActivationLink getByToken(String token) {
        return emailRepository.getByToken(token);
    }

    @Override
    public ActivationLink save(ActivationLink activationLink) {
        return emailRepository.save(activationLink);
    }


}
