package br.app.pregsrateio.common.security;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Repository;

import br.app.pregsrateio.usuario.service.CrudUsuarioService;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserPrincipalCacheRepository {
    private final CrudUsuarioService crudUsuarioService;
    private final CustomAuthorityConverter customAuthorityConverter;

    @Cacheable(cacheNames = "principals", key = "#tokenHash")
    public UsuarioLogado carregar(String tokenHash, Jwt jwt) {
        var usuario = crudUsuarioService.buscarPorAuthSub(jwt.getSubject());
        var authorities = customAuthorityConverter.convert(jwt);

        return new UsuarioLogado(jwt, usuario, authorities);
    }
}