package com.jm.jamesapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jm.jamesapp.models.user.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.jwtExpirationH}")
    private Integer expirationH;

    @Value("${api.security.token.zoneOffSet}")
    private String zoneOffSet;

    @Value("${api.security.token.issuer}")
    private String issuer;

    public String generateToken(UserModel user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getUsername())
                    .withKeyId(String.valueOf(user.getId()))
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public UUID validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            UUID id = UUID.fromString(JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getKeyId());
            return id;
        } catch (JWTVerificationException exception) {
            return UUID.randomUUID();
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(expirationH).toInstant(ZoneOffset.of(zoneOffSet));
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getExpirationH() {
        return expirationH;
    }

    public void setExpirationH(Integer expirationH) {
        this.expirationH = expirationH;
    }

    public String getZoneOffSet() {
        return zoneOffSet;
    }

    public void setZoneOffSet(String zoneOffSet) {
        this.zoneOffSet = zoneOffSet;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
