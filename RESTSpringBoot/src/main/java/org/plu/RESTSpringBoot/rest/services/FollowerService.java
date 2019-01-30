package org.plu.RESTSpringBoot.rest.services;

import org.plu.RESTSpringBoot.entities.Followers;

import java.util.HashMap;

public interface FollowerService {
    Followers follow(Followers f);
    int unfollow(Followers f);

    HashMap<String,String> getProfile(String username);

    Followers getByFollowerIdAndUserId(String followerId, String userId);
}
