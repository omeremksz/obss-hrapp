package com.omer.hrapp.security;

import com.omer.hrapp.entities.Specialist;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

public class JwtTokenProvider {

    public static String generateToken(String userName) throws Exception {
        try {
            Map<String, Object> claims = new HashMap<>();
            Instant now = Instant.now();
            String jwtToken = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userName)
                    .setId(UUID.randomUUID().toString())
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(51, ChronoUnit.MINUTES)))
                    .signWith(SignatureAlgorithm.RS512, getPrivateKey())
                    .compact();
            return jwtToken;
        } catch (Exception e) {
            throw new Exception("Failed to generate token", e);
        }

    }

    private static Key getPrivateKey() throws Exception {
        try {
            ClassLoader classLoader = JwtTokenProvider.class.getClassLoader();
            File file = new File(classLoader.getResource("private.key").getFile());
            byte [] rsaPrivateKeyArr = FileUtils.readFileToByteArray(file);
            String rsaPrivateKey = new String(rsaPrivateKeyArr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(rsaPrivateKey));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new Exception("Failed to get private key", e);
        }
    }

    public Boolean validateToken(String token, Specialist userDetails) throws Exception {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUserName()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) throws Exception {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws Exception {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws Exception {
        return Jwts.parser().setSigningKey(getPrivateKey())
                .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) throws Exception {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) throws Exception {
        return getClaimFromToken(token, Claims::getExpiration);
    }

}
