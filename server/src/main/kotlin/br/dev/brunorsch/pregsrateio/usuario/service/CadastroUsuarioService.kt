package br.dev.brunorsch.pregsrateio.usuario.service

import br.dev.brunorsch.pregsrateio.common.erros.ControleFluxoException
import br.dev.brunorsch.pregsrateio.common.log
import br.dev.brunorsch.pregsrateio.usuario.api.dto.CadastroUsuarioRequest
import br.dev.brunorsch.pregsrateio.usuario.model.Usuario
import br.dev.brunorsch.pregsrateio.usuario.model.UsuarioRepository
import jakarta.inject.Singleton

@Singleton
class CadastroUsuarioService(
    private val usuarioRepository: UsuarioRepository,
) {
    fun validarCadastroCompleto(authSub: String): Boolean {
        val usuario = usuarioRepository.findByAuthSub(authSub)

        return usuario?.isCadastroCompleto ?: false
    }

    fun cadastrar(request: CadastroUsuarioRequest): Usuario {
        log().info("Cadastrando usuário: [${request.authSub}]")

        if(validarCadastroCompleto(request.authSub)) {
            log().warn("Usuário com cadastro já finalizado.")

            throw ControleFluxoException(
                "USUARIO_JA_CADASTRADO",
                "Você já está cadastrado na plataforma.")
        }

        return usuarioRepository.save(request.toUsuario())
    }
}