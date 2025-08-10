package br.app.pregsrateio.common.security;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;

import br.app.pregsrateio.usuario.data.Usuario;
import lombok.Data;

@Data
public class UsuarioPrincipal implements UserDetails {
    private final String suid;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Usuario usuario;
    private final ObjectId idUsuario;

    public static UsuarioPrincipal fromJwt(Jwt jwt, Usuario usuario) {
        String suid = jwt.getClaimAsString("suid");
        String username = jwt.getClaimAsString("username");
        String password = jwt.getClaimAsString("password"); // Pode ser null ou vazio, dependendo do uso
        Collection<? extends GrantedAuthority> authorities = jwt.getClaimAsStringList("roles").stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role.toUpperCase())
                .toList();

        return new UsuarioPrincipal(suid, username, password, authorities, usuario, usuario.getId());
    }
}
