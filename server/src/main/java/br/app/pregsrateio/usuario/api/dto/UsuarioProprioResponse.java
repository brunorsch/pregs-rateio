package br.app.pregsrateio.usuario.api.dto;

import br.app.pregsrateio.usuario.data.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;
import static java.util.Optional.ofNullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioProprioResponse {
    private String id;
    private String authSub;
    private String nome;
    private String sobrenome;
    private String email;
    private String whatsApp;
    private String fotoUrl;

    public static UsuarioProprioResponse from(Usuario usuario) {
        return UsuarioProprioResponse.builder()
                .id(ofNullable(usuario.getId()).map(ObjectId::toHexString).orElse(null))
                .authSub(usuario.getAuthSub())
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .email(usuario.getEmail())
                .whatsApp(usuario.getWhatsApp())
                .fotoUrl(usuario.getFotoUrl())
                .build();
    }
}
