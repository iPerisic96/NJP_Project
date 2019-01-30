package org.plu.RESTSpringBoot.rest.services;

import org.plu.RESTSpringBoot.entities.ActivationLink;
import org.plu.RESTSpringBoot.entities.ApplicationUser;

public interface UserService {
    ApplicationUser saveUnregistredUser(ApplicationUser user);

    ApplicationUser findByUsername(String username);

    boolean checkPassword(ApplicationUser u, String password);
    boolean validateUser (ApplicationUser user);

    ActivationLink getVerificationToken(String token);

    ApplicationUser save(ApplicationUser user);

    boolean isValidated(String username);
}
