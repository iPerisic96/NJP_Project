package org.plu.RESTSpringBoot.repository;

import org.plu.RESTSpringBoot.entities.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);

    @Query(value = "select validated from user where username = :username", nativeQuery = true)
    boolean isValidated(@Param("username") String username);
}
