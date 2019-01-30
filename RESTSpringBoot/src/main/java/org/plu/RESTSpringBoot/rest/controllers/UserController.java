package org.plu.RESTSpringBoot.rest.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.plu.RESTSpringBoot.entities.ActivationLink;
import org.plu.RESTSpringBoot.entities.Followers;
import org.plu.RESTSpringBoot.entities.Post;
import org.plu.RESTSpringBoot.entities.ApplicationUser;
import org.plu.RESTSpringBoot.rest.services.FollowerService;
import org.plu.RESTSpringBoot.rest.services.PostService;
import org.plu.RESTSpringBoot.rest.services.UserService;
import org.plu.RESTSpringBoot.util.ImageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@CrossOrigin
@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private FollowerService followerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private MessageSource messages;

    @PostMapping(value = "auth/sign-up")
    public ApplicationUser registration(@RequestBody ApplicationUser user) {
        return userService.saveUnregistredUser(user);
    }

//    @PostMapping(value = "/login")
//    public ApplicationUser login(@RequestBody ApplicationUser user) {
//        System.out.println("Log in ruta");
//        String postman_password = user.getPassword();
//        ApplicationUser u = userService.findByUsername(user.getUsername());
//
//        if (userService.checkPassword(u, postman_password) && user.getValidated()) {
//            return u;
//        }
//
//        return null;
//    }

    @RequestMapping(value = "/postImage/{username}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> postImage(@RequestParam("file") MultipartFile file, @PathVariable("username") String username) throws IOException {

        String path = ImageUtil.saveImage(file);
        ApplicationUser u = userService.findByUsername(username);
        Post post = new Post();
        post.setPostPath(path);
        post.setUserId(u.getId());
        postService.postImage(post);
        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public Followers follow(@RequestBody HashMap<String, String> follower) {
        ApplicationUser following = userService.findByUsername(follower.get("followingId"));
        ApplicationUser user = userService.findByUsername(follower.get("userId"));
        Followers f = new Followers();
        f.setFollowingId(following.getId());
        f.setUserId(user.getId());
        return followerService.follow(f);
    }

    @RequestMapping(value = "/unfollow", method = RequestMethod.POST)
    public int unfollow(@RequestBody HashMap<String, String> follower) {
        ApplicationUser following = userService.findByUsername(follower.get("followingId"));
        ApplicationUser user = userService.findByUsername(follower.get("userId"));
        Followers f = new Followers();
        f.setFollowingId(following.getId());
        f.setUserId(user.getId());
        return followerService.unfollow(f);
    }

    @GetMapping(value = "posts/count")
    public int countAllPosts() {
        return postService.countAll();
    }

    @RequestMapping(value = "myPosts/{limit}/{offset}", method = RequestMethod.GET)
    public List<Object[]> getPost(@PathVariable("limit") int limit, @PathVariable("offset") int offset) {
        return postService.selectAll(limit, offset);
    }

    @RequestMapping(value = "getImage/{url}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getPostImage(@PathVariable("url") String url) {
        return ImageUtil.getFile("C:/instagramPhotos/" + url + ".jpg");
    }

    @RequestMapping(value = "profile/{username}", method = RequestMethod.GET)
    public HashMap<String, String> getPost(@PathVariable("username") String username) {
        //slika
        return followerService.getProfile(username);
    }

    @RequestMapping(value = "isFollowing", method = RequestMethod.POST)
    public boolean isFollowing(@RequestBody HashMap<String, String> follower) {
        ApplicationUser following = userService.findByUsername(follower.get("followingId"));
        ApplicationUser user = userService.findByUsername(follower.get("userId"));
        return followerService.getByFollowerIdAndUserId(follower.get("followerId"), follower.get("userId")) != null;
    }

    @GetMapping(value = "auth/registrationConfirm")
    public boolean confirmRegistration(@RequestParam("token") String token) {

        ActivationLink verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            //TODO
        }

        ApplicationUser user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            //TODO
        }
        return userService.validateUser(user);
//        user.setValidated(true);
//        userService.save(user);
    }

    @GetMapping(value = "auth/{username}")
    public boolean isVerified(@PathVariable() String username){

        if (userService.findByUsername(username) != null){
            return userService.isValidated(username);
        }
        return false;
    }

}