package br.app.pregsrateio.usuario.api.dto;

import br.app.pregsrateio.usuario.data.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastroUsuarioRequest {

    private String nome;
    private String sobrenome;
    private String email;
    private String numeroCelular;
    private String fotoUrl;

    public Usuario toUsuario(String authSub) {
        return Usuario.builder()
                .authSub(authSub)
                .nome(nome)
                .sobrenome(sobrenome)
                .email(email)
                .numeroCelular(numeroCelular)
                .fotoUrl(fotoUrl)
                .build();
    }
}
