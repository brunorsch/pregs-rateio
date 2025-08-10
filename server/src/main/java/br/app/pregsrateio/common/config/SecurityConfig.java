package br.app.pregsrateio.common.config;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import br.app.pregsrateio.common.security.CustomAuthorityConverter;
import br.app.pregsrateio.common.security.UserPrincipalCacheRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    @Order(1)
    @Deprecated
    public SecurityFilterChain cadastroChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/usuarios/**")
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(withAuthorityConverter())));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter withAuthorityConverter() {
        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(customAuthorityConverter());
        return converter;
    }

    @Bean
    public CustomAuthorityConverter customAuthorityConverter() {
        return new CustomAuthorityConverter();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomJwtConverter jwtConverter) throws Exception {
        http
            .anonymous(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**", "/docs/**", "/swagger-ui.html**", "/v3/api-docs**").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

        return http.build();
    }

    @Bean
    public CustomJwtConverter jwtConverter(@Autowired UserPrincipalCacheRepository userPrincipalCacheRepository) {
        return new CustomJwtConverter(userPrincipalCacheRepository);
    }

    @RequiredArgsConstructor
    public static class CustomJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
        private final UserPrincipalCacheRepository userPrincipalCacheRepository;

        @Override
        public final AbstractAuthenticationToken convert(Jwt jwt) {
            System.out.println("Chegou na conversao");
            var hash = md5Hex(jwt.getTokenValue());

            return userPrincipalCacheRepository.carregar(hash, jwt);
        }
    }

}
