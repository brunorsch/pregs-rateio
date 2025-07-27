package br.dev.brunorsch.pregsrateio.recorrencia

import br.dev.brunorsch.pregsrateio.common.erros.ControleFluxoException
import br.dev.brunorsch.pregsrateio.common.log
import br.dev.brunorsch.pregsrateio.common.toObjectId
import br.dev.brunorsch.pregsrateio.recorrencia.api.dto.CriacaoRecorrenciaRequest
import br.dev.brunorsch.pregsrateio.recorrencia.api.dto.toRecorrencia
import br.dev.brunorsch.pregsrateio.recorrencia.model.Recorrencia
import br.dev.brunorsch.pregsrateio.recorrencia.model.RecorrenciaRepository
import br.dev.brunorsch.pregsrateio.usuario.service.UsuarioCrudService
import io.micronaut.http.HttpStatus
import jakarta.inject.Singleton

@Singleton
class RecorrenciaCrudService(
    private val recorrenciaRepository: RecorrenciaRepository,
    private val usuarioCrudService: UsuarioCrudService,
) {
    fun cadastrar(authSub: String, request: CriacaoRecorrenciaRequest): Recorrencia {
        val usuario = usuarioCrudService.buscarPorAuthSub(authSub)

        log().info("Criando recorrência para o usuário: [{}] - [{}]",
            usuario.id, request)

        val recorrenciaNova = request.toRecorrencia(usuario.id!!)

        return recorrenciaRepository.save(recorrenciaNova)
    }

    fun buscarPorId(authSub: String, id: String): Recorrencia {
        log().trace("Buscando recorrência pelo ID: [{}]", id)

        return recorrenciaRepository.findById(id.toObjectId())
            .filter { it.isUsuarioAutorizado(authSub.toObjectId()) }
            .orElseThrow { erroRecorrenciaNaoEncontrada() }
    }

    private fun erroRecorrenciaNaoEncontrada(): ControleFluxoException {
        return ControleFluxoException(
            codigo = "RECORRENCIA_NAO_ENCONTRADA",
            mensagem = "Recorrência não encontrada",
            status = HttpStatus.NOT_FOUND
        )
    }
}