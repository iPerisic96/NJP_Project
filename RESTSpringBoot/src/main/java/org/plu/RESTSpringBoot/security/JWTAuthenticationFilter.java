package org.plu.RESTSpringBoot.security;

import com.auth0.jwt.JWT;
import org.plu.RESTSpringBoot.entities.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


import static org.plu.RESTSpringBoot.security.SecurityConstants.EXPIRATION_TIME;
import static org.plu.RESTSpringBoot.security.SecurityConstants.HEADER_STRING;
import static org.plu.RESTSpringBoot.security.SecurityConstants.SECRET;
import static org.plu.RESTSpringBoot.security.SecurityConstants.TOKEN_PREFIX;

/**
 * Sluzi da da JSON Web Token user-u koji pokusava da pristupi (user salje username i password).
 *
 * Ova klasa nasledjuje UsernamePasswordAuthenticationFilter koja nasledjuje AbstractAuthenticationProcessingFilter
 * Ocekuje da u login formi imamo dva parametra: username i password
 * Ukoliko hocemo da promenimo ove default vrednosti moramo da uradimo override settera za parametre: usernameParameter i passwordParameter
 * Po defaultu ocekuje da je URL /login
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //interfejs a nama vazna metoda je: authenticate
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            ApplicationUser creds = new ObjectMapper()
                    .readValue(req.getInputStream(), ApplicationUser.class);

            /*
            Dobra praksa je da se koristi samo sa AuthenticationManager interfejsom.
             */
                return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                creds.getUsername(),
                                creds.getPassword(),
                                new ArrayList<>())
                );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

        res.getWriter().print(((User) auth.getPrincipal()).getUsername());
    }
}