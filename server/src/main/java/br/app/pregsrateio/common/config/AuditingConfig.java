package br.app.pregsrateio.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import br.app.pregsrateio.common.auditoria.SpringSecurityAuditorAware;

@Configuration
@EnableMongoAuditing
public class AuditingConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware();
    }
}
