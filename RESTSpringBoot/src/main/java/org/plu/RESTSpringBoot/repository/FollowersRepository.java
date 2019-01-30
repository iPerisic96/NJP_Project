package org.plu.RESTSpringBoot.repository;

import org.plu.RESTSpringBoot.entities.Followers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FollowersRepository extends JpaRepository<Followers, Integer> {
    @Transactional
    int deleteByFollowingIdAndUserId(int followingId, int userId);

    int countByFollowingId(int followingId);

    int countByUserId(int userId);

    Followers findByFollowingIdAndUserId(int followingId, int userId);
}
