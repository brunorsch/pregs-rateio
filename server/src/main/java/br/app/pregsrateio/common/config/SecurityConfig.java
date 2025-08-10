package br.app.pregsrateio.common.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.DigestUtils;

import br.app.pregsrateio.common.security.UserPrincipalCacheRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String CUSTOM_CLAIM = "https://api.pregsrateio.app.br/claims/roles";

    private final UserPrincipalCacheRepository userPrincipalCacheRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/docs/**", "/swagger-ui.html**", "/v3/api-docs**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter())));

        return http.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtConverter() {
        return jwt -> {
            List<String> roles = jwt.getClaimAsStringList(CUSTOM_CLAIM);
            if (roles == null) {
                roles = List.of();
            }
            var authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());

            var hash = new String(
                DigestUtils.md5Digest(jwt.getTokenValue().getBytes()));

            var principal = userPrincipalCacheRepository
                .carregar(hash, jwt);

            return new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
        };
    }
}
