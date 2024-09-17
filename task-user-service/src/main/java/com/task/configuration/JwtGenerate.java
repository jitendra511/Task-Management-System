package com.task.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtGenerate {

    static SecretKey secretKey= Keys.hmacShaKeyFor(JwtToken.SECRET_KEY.getBytes());

    public static String generateToken(Authentication authentication)
    {
        Collection<? extends GrantedAuthority> grantedAuthorities=authentication.getAuthorities();
        String roles=populateAuthorities(grantedAuthorities);
        String jwt= Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",authentication.getName())
                .claim("authorities",roles)
                .signWith(secretKey)
                .compact();
        return jwt;
    }

    public static String getEmailFromJwtToken(String jwt)
    {
        jwt=jwt.substring(7);
        Claims claims= Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        String email=String.valueOf(claims.get("email"));
        return email;
    }

    public static String populateAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities)
    {
        Set<String> auths=new HashSet<>();
        for(GrantedAuthority grantedAuthority:grantedAuthorities)
        {
            auths.add(grantedAuthority.getAuthority());
        }
        return String.join(",",auths);
    }
}
