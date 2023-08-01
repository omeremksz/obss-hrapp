package com.omer.hrapp.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${hrapp.app.secret}")
    private String APP_SECRET;
    @Value("${hrapp.expires.in}")
    private Long EXPIRES_IN;

    public String generateJwtToken(Authentication auth){
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expiredDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date()).setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }

    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJwt(token).getBody();
        return claims.getSubject();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJwt(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJwt(token);
            return !isTokenExpired(token);
        }catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}