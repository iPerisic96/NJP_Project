package org.plu.RESTSpringBoot.security;

/**
 * Konstante koje nam trebaju. Poslednja moze da bude bilo koji url zelite da bude za singup formu
 */
public final class SecurityConstants {
    public static final String SECRET = "Ivan";
    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Basic ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/auth/sign-up";
}