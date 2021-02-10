package com.gustavofernandez.exchangerate.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;

@Component
@Slf4j
public class JwtService {

  private static final String SECRET = "s3cr3+";
  private static final long TTL_MILLIS = Duration.ofMinutes(10).toMillis();

  public String generateJwt(String roles) {
    Claims claims = Jwts.claims();
    claims.put("roles", roles);

    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + TTL_MILLIS))
        .signWith(SignatureAlgorithm.HS256, SECRET)
        .compact();
  }

  public boolean validateJwt(String jwt, String jwtActionName) {
    try {
      Jws<Claims> claimsJws = Jwts.parser()
          .setSigningKey(SECRET)
          .parseClaimsJws(jwt);

      Object roles = claimsJws.getBody().get("roles");
      if (roles instanceof String) {
        boolean hasPermission = Arrays
            .stream(((String) roles).split(","))
            .anyMatch(s -> s.equals(jwtActionName));
        log.info("Roles: '{}', HasPermission: {}", roles, hasPermission);
        return hasPermission;
      }
      return false;
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT: {}", e.getMessage());
      return false;
    } catch (Exception e) {
      log.error("Error caught validating JWT", e);
      return false;
    }
  }

}
