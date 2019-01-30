package org.plu.RESTSpringBoot.rest.services;

import org.plu.RESTSpringBoot.entities.Post;

import java.util.List;

public interface PostService {
    Post postImage(Post p);

    int countAll();
    List<Object[]> selectAll(int limit, int offset);
}
