package org.plu.RESTSpringBoot.rest.services;

import org.plu.RESTSpringBoot.entities.ApplicationUser;
import org.plu.RESTSpringBoot.entities.Followers;
import org.plu.RESTSpringBoot.repository.FollowersRepository;
import org.plu.RESTSpringBoot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class FollowerServiceImpl implements FollowerService {


    @Autowired
    private FollowersRepository followerRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private PostRepository postRepository;

    @Override
    public Followers follow(Followers f) {
        return followerRepository.save(f);
    }

    @Override
    public int unfollow(Followers f) {
        return followerRepository.deleteByFollowingIdAndUserId(f.getFollowingId(), f.getUserId());
    }

    @Override
    public HashMap<String, String> getProfile(String username) {

        ApplicationUser u = userService.findByUsername(username);
        HashMap<String, String> map = new HashMap<>();
        // koliko ljudi ja pratim, koliko puta se ja nalazim u koloni userId, koja oznacava coveka koji je zapratio
        int following = followerRepository.countByUserId(u.getId());
        // koliko ljudi mene prati
        int followers = followerRepository.countByFollowingId(u.getId());

        int posts = postRepository.countByUserId(u.getId());

        map.put("following", "" + following);
        map.put("followers", "" + followers);
        map.put("posts", "" + posts);
        map.put("username", "" + u.getUsername());


        return map;
    }

    @Override
    public Followers getByFollowerIdAndUserId(String followingId, String userId) {
        ApplicationUser following = userService.findByUsername(followingId);
        ApplicationUser user = userService.findByUsername(userId);
        return followerRepository.findByFollowingIdAndUserId(following.getId(), user.getId());
    }
}
