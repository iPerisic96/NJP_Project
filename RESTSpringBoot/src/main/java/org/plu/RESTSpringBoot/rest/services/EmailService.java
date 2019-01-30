package org.plu.RESTSpringBoot.rest.services;

import org.plu.RESTSpringBoot.entities.ActivationLink;
import org.plu.RESTSpringBoot.entities.ApplicationUser;

public interface EmailService {

    boolean confirmActivation(String tok);

    ActivationLink getByToken(String token);
    ActivationLink save(ActivationLink activationLink);
}
