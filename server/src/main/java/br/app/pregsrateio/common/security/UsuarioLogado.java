package br.app.pregsrateio.common.security;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import br.app.pregsrateio.usuario.data.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = "idUsuario")
@EqualsAndHashCode(callSuper = true)
public class UsuarioLogado extends JwtAuthenticationToken {
    private final Usuario usuario;
    private final ObjectId idUsuario;

    public UsuarioLogado(Jwt jwt, Usuario usuario, Collection<GrantedAuthority> authorities) {
        super(jwt, authorities);
        this.usuario = usuario;
        this.idUsuario = usuario.getId();
    }
}
