package org.plu.RESTSpringBoot.rest.services;

import org.plu.RESTSpringBoot.entities.ActivationLink;
import org.plu.RESTSpringBoot.entities.ApplicationUser;
import org.plu.RESTSpringBoot.repository.ApplicationUserRepository;
import org.plu.RESTSpringBoot.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static java.util.Collections.emptyList;


@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ApplicationUser saveUnregistredUser(ApplicationUser user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            ApplicationUser u = userRepository.save(user);
            String token = EmailUtil.sendVerificationMail(u.getEmail());
            emailService.save(new ActivationLink(u, token));
            return u;
        }
        return null;
    }

    @Override
    public ApplicationUser save(ApplicationUser user){
        return userRepository.save(user);
    }

    @Override
    public ApplicationUser findByUsername(String username) {
        System.out.println(username);
        return userRepository.findByUsername(username);

    }

    @Override
    public boolean checkPassword(ApplicationUser u, String password) {

        return (u.getPassword().equals(password)) ? true : false;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ApplicationUser u = userRepository.findByUsername(s);

        if (u == null) {
            throw new UsernameNotFoundException(s);
        }
        return new User(u.getUsername(), u.getPassword(), emptyList());

    }

    @Override
    public boolean validateUser (ApplicationUser user){
        if (!user.getValidated()){
            user.setValidated(true);
            userRepository.save(user);
            return true;
        }
        if (user.getValidated()){
            return true;
        }
        return false;
    }

    @Override
    public ActivationLink getVerificationToken(String VerificationToken) {
        return emailService.getByToken(VerificationToken);
    }

    @Override
    public boolean isValidated(String username){
       return userRepository.isValidated(username);
    }
}
