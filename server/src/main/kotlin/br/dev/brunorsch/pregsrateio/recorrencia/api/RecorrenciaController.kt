package br.dev.brunorsch.pregsrateio.recorrencia.api

import br.dev.brunorsch.pregsrateio.recorrencia.RecorrenciaCrudService
import br.dev.brunorsch.pregsrateio.recorrencia.api.dto.CriacaoRecorrenciaRequest
import br.dev.brunorsch.pregsrateio.recorrencia.api.dto.RecorrenciaResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid

@Tag(
    name = "Recorrências",
    description = "Gestão das recorrências para rateio de gastos recorrentes"
)
@Controller("/recorrencias")
open class RecorrenciaController(
    private val recorrenciaCrudService: RecorrenciaCrudService
) {
    @Post
    open fun postRecorrencia(
        @Body @Valid request: CriacaoRecorrenciaRequest,
        auth: Authentication
    ): RecorrenciaResponse =
        recorrenciaCrudService.cadastrar(auth.attributes["sub"] as String, request)
            .let { RecorrenciaResponse.from(it) }
}