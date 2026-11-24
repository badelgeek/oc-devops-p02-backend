package com.openclassrooms.etudiant.configuration.security;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration 
public class JwtConfig {

  @Bean
  public SecretKey jwtSecretKey(@Value("${app.jwt.secret}") String secret) {
    byte[] keyBytes = Base64.getDecoder().decode(secret);
    if (keyBytes.length < 32) {
      throw new IllegalArgumentException("JWT secret must decode to at least 32 bytes for HS256");
    }
    SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");
    return key;
  }

  @Bean
  public JwtEncoder jwtEncoder(SecretKey jwtSecretKey) {
    JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<>(jwtSecretKey);
    return new NimbusJwtEncoder(immutableSecret);
  }

  @Bean
  public JwtDecoder jwtDecoder(SecretKey jwtSecretKey) {
    return NimbusJwtDecoder.withSecretKey(jwtSecretKey).build();
  }

}
