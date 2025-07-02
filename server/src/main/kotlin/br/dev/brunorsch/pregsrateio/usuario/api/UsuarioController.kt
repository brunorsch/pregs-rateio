package br.dev.brunorsch.pregsrateio.usuario.api

import br.dev.brunorsch.pregsrateio.usuario.api.dto.CadastroUsuarioRequest
import br.dev.brunorsch.pregsrateio.usuario.api.dto.UsuarioProprioResponse
import br.dev.brunorsch.pregsrateio.usuario.service.CadastroUsuarioService
import io.micronaut.data.model.query.factory.Projections.id
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponseFactory
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.HttpStatus.PRECONDITION_FAILED
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.annotation.Status
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(
    name = "Usuários",
    description = "Gestão e cadastro dos usuários da aplicação (Organizadores)"
)
@Controller("/usuarios")
open class UsuarioController(
    private val cadastroUsuarioService: CadastroUsuarioService,
) {
    @Get("/completude")
    @Status(OK)
    @Operation(
        summary = "Verificar se o cadastro do usuário está completo",
        description = "Verifica se o usuário possui todos os dados necessários " +
                "para ser considerado completo. O endpoint pode ser usado para " +
                "verificar se o usuário recem cadastrado no Auth0 precisa passar pelo " +
                "fluxo de onboarding"
    )
    @ApiResponse(responseCode = "200", description = "Cadastro regularizado")
    @ApiResponse(responseCode = "412", description = "Cadastro incompleto")
    open fun getCompletude(
        @QueryValue sub: String
    ): HttpResponse<Unit> {
        val isCompleto = cadastroUsuarioService.validarCadastroCompleto(sub)

        return HttpResponse.status(if(isCompleto) OK else PRECONDITION_FAILED)
    }

    @Post
    @Status(CREATED)
    @Operation(
        summary = "Cadastrar usuário",
        description = "Realiza o cadastro de um novo usuário na aplicação. O usuário " +
                "precisa ter sua conta já criada no Auth0 para concluir o cadastro."
    )
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    open fun postUsuario(
        @Body request: CadastroUsuarioRequest
    ): UsuarioProprioResponse =
        cadastroUsuarioService.cadastrar(request)
            .let { UsuarioProprioResponse.from(it) }
}