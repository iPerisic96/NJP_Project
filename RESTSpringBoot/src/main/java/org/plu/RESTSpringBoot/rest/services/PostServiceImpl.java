package org.plu.RESTSpringBoot.rest.services;

import org.plu.RESTSpringBoot.entities.Post;
import org.plu.RESTSpringBoot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post postImage(Post p) {
        p.setCreated(System.currentTimeMillis());
        return postRepository.save(p);
    }

    @Override
    public int countAll() {
        return postRepository.countAll();
    }

    @Override
    public List<Object[]> selectAll(int limit, int offset) {
        return postRepository.selectAll(limit,offset);
    }

}