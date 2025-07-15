package br.dev.brunorsch.pregsrateio.recorrencia

import br.dev.brunorsch.pregsrateio.common.log
import br.dev.brunorsch.pregsrateio.recorrencia.api.dto.CriacaoRecorrenciaRequest
import br.dev.brunorsch.pregsrateio.recorrencia.api.dto.RecorrenciaResponse
import br.dev.brunorsch.pregsrateio.recorrencia.api.dto.toRecorrencia
import br.dev.brunorsch.pregsrateio.recorrencia.model.Recorrencia
import br.dev.brunorsch.pregsrateio.recorrencia.model.RecorrenciaRepository
import br.dev.brunorsch.pregsrateio.usuario.service.UsuarioCrudService
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
}