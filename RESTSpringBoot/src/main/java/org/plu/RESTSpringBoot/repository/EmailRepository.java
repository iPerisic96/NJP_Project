package org.plu.RESTSpringBoot.repository;

import org.plu.RESTSpringBoot.entities.ActivationLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<ActivationLink, Long> {
    ActivationLink getByToken(String token);
}
