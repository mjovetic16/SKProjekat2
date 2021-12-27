package com.example.skpr2.skprojekat2notificationservice.security.service.impl;



import com.example.skpr2.skprojekat2notificationservice.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${oauth.jwt.secret}")
    private String jwtSecret;


    @Override
    public Claims parseToken(String jwt) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            System.out.println("Couldn't parse token: "+jwt);
            return null;
        }
        System.out.println(claims);
        return claims;
    }
}
