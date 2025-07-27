package br.dev.brunorsch.pregsrateio.recorrencia.api

import br.dev.brunorsch.pregsrateio.recorrencia.RecorrenciaCrudService
import br.dev.brunorsch.pregsrateio.recorrencia.api.dto.CriacaoRecorrenciaRequest
import br.dev.brunorsch.pregsrateio.recorrencia.api.dto.RecorrenciaResponse
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
    @Status(CREATED)
    @Operation(
        summary = "Criar uma recorrência",
        description = "Cria uma nova recorrência de gastos recorrentes. " +
                "A recorrência é a entidade central, que representa um gasto " +
                "que se repete periodicamente, e que pode ser rateada com amigos."
    )
    @ApiResponse(responseCode = "201", description = "Recorrência criada")
    open fun postRecorrencia(
        @Body @Valid request: CriacaoRecorrenciaRequest,
        auth: Authentication
    ): RecorrenciaResponse =
        recorrenciaCrudService.cadastrar(auth.attributes["sub"] as String, request)
            .let { RecorrenciaResponse.from(it) }

    @Get("/{id}")
    @Status(OK)
    @Operation(
        summary = "Consultar uma recorrência",
        description = "Consultar uma recorrência específica pelo ID."
    )
    @ApiResponse(responseCode = "200", description = "Recorrência encontrada")
    @ApiResponse(responseCode = "404", description = "Recorrência não encontrada")
    open fun getRecorrencia(
        id: String,
        auth: Authentication
    ): RecorrenciaResponse =
        recorrenciaCrudService.buscarPorId(auth.attributes["sub"] as String, id)
            .let { RecorrenciaResponse.from(it) }
}