package br.dev.brunorsch.pregsrateio.usuario.service

import br.dev.brunorsch.pregsrateio.common.erros.ControleFluxoException
import br.dev.brunorsch.pregsrateio.common.log
import br.dev.brunorsch.pregsrateio.usuario.api.dto.CadastroUsuarioRequest
import br.dev.brunorsch.pregsrateio.usuario.model.Usuario
import br.dev.brunorsch.pregsrateio.usuario.model.UsuarioRepository
import io.micronaut.http.HttpStatus.NOT_FOUND
import jakarta.inject.Singleton

@Singleton
class UsuarioCrudService(
    private val usuarioRepository: UsuarioRepository,
) {
    fun buscarPorAuthSub(authSub: String): Usuario {
        log().trace("Buscando usuário por Sub: [{}]", authSub)

        return usuarioRepository.findByAuthSub(authSub)
            ?: throw ControleFluxoException(
                codigo ="USUARIO_NAO_ENCONTRADO",
                mensagem = "Usuário não encontrado.",
                status = NOT_FOUND
            )
    }

    fun validarCadastroCompleto(authSub: String): Boolean {
        val usuario = usuarioRepository.findByAuthSub(authSub)

        return usuario?.isCadastroCompleto ?: false
    }

    fun cadastrar(authSub: String, request: CadastroUsuarioRequest): Usuario {
        log().info("Cadastrando usuário: [${authSub}]")

        if(validarCadastroCompleto(authSub)) {
            log().warn("Usuário com cadastro já finalizado.")

            throw ControleFluxoException(
                "USUARIO_JA_CADASTRADO",
                "Você já está cadastrado na plataforma.")
        }

        return usuarioRepository.save(request.toUsuario(authSub))
    }
}