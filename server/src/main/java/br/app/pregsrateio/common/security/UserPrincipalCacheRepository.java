package br.app.pregsrateio.common.security;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Repository;

import br.app.pregsrateio.usuario.CrudUsuarioService;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserPrincipalCacheRepository {
    private final CrudUsuarioService crudUsuarioService;

    @Cacheable(cacheNames = "principals", key = "#tokenHash")
    public UsuarioPrincipal carregar(String tokenHash, Jwt jwt) {
        var usuario = crudUsuarioService.buscarPorAuthSub(jwt.getSubject());

        return UsuarioPrincipal.fromJwt(jwt, usuario);
    }
}