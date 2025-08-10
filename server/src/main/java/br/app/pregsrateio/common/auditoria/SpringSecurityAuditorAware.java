package br.app.pregsrateio.common.auditoria;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import br.app.pregsrateio.common.security.UsuarioLogado;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        UsuarioLogado usuer = (UsuarioLogado) SecurityContextHolder.getContext()
            .getAuthentication();

        if (usuer == null || !usuer.isAuthenticated()) {
            return empty();
        }

        return of(usuer.getIdUsuario().toHexString());
    }
}