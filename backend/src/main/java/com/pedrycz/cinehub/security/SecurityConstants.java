package com.pedrycz.cinehub.security;

public class SecurityConstants {
    public static final String[] AUTHORIZATION_WHITELIST = {
            "/documentation/**",
            "/auth/**",
            "/auth/register",
            "/auth/authenticate",
            "/user/nickname/**",
            "/user/id/**",
            "/poster/**",
            "/movies/all/**",
            "/movies/id/**",
            "/movies/title/**",
            "/movies/director/**",
            "/movies/actor/**",
            "/movies/genre/**",
            "/movies/runtime/**",
            "/review/id/**",
            "/review/movie/**",
            "/review/user/**",
            "/swagger-ui.html", "/docs", "/swagger-resources/**",
            "/swagger-resources", "/v3/api-docs/**", "/swagger-ui/**",
            "/swagger-ui/favicon-32x32.png", "/swagger-ui/favicon-16x16.png",
            "/webjars/swagger-ui/**", "/proxy/**"
    };
    
    public static final String[] ADMIN_ONLY = {
            "/movies/add",
            "/movies/delete/**"
    };
}
