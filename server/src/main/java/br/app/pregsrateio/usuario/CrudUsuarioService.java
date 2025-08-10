package br.app.pregsrateio.usuario;

import br.app.pregsrateio.common.erros.ControleFluxoException;
import br.app.pregsrateio.usuario.api.dto.CadastroUsuarioRequest;
import br.app.pregsrateio.usuario.data.Usuario;
import br.app.pregsrateio.usuario.data.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrudUsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario buscarPorAuthSub(String authSub) {
        log.debug("Buscando usuário por Sub: [{}]", authSub);

        return usuarioRepository.findByAuthSub(authSub)
                .orElseThrow(() -> new ControleFluxoException(
                        "USUARIO_NAO_ENCONTRADO",
                        "Usuário não encontrado.",
                        NOT_FOUND));
    }

    @Transactional
    public Usuario cadastrar(String authSub, CadastroUsuarioRequest request) {
        log.info("Cadastrando usuário: [{}]", authSub);

        if (validarCadastroCompleto(authSub)) {
            log.warn("Usuário com cadastro já finalizado.");

            throw new ControleFluxoException(
                    "USUARIO_JA_CADASTRADO",
                    "Você já está cadastrado na plataforma.");
        }

        var usuario = request.toUsuario(authSub);

        return usuarioRepository.save(usuario);
    }

    public boolean validarCadastroCompleto(String authSub) {
        return usuarioRepository.findByAuthSub(authSub)
                .map(Usuario::isCadastroCompleto)
                .orElse(false);
    }
}

