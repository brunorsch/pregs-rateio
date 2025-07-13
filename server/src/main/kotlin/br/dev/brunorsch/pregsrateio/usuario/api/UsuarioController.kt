package br.dev.brunorsch.pregsrateio.usuario.api

import br.dev.brunorsch.pregsrateio.usuario.api.dto.CadastroUsuarioRequest
import br.dev.brunorsch.pregsrateio.usuario.api.dto.UsuarioProprioResponse
import br.dev.brunorsch.pregsrateio.usuario.service.UsuarioCrudService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus.*
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid

@Tag(
    name = "Usuários",
    description = "Gestão e cadastro dos usuários da aplicação (Organizadores)"
)
@Secured("PGRT_USER")
@Controller("/usuarios")
open class UsuarioController(
    private val usuarioCrudService: UsuarioCrudService,
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
        auth: Authentication,
    ): HttpResponse<Unit> {
        val sub = auth.attributes["sub"] as String
        val isCompleto = usuarioCrudService.validarCadastroCompleto(sub)

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
        auth: Authentication,
        @Body @Valid request: CadastroUsuarioRequest
    ): UsuarioProprioResponse =
        usuarioCrudService.cadastrar(auth.attributes["sub"] as String, request)
            .let { UsuarioProprioResponse.from(it) }

    @Get("/me")
    @Status(OK)
    @Operation(
        summary = "Buscar dados do usuário logado",
        description = "Busca os dados do usuário logado na aplicação. " +
                "O usuário precisa estar autenticado e o token JWT deve ser " +
                "enviado no header Authorization."
    )
    @ApiResponse(responseCode = "200", description = "Dados do usuário encontrados")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    open fun getUsuarioLogado(
        auth: Authentication,
    ): UsuarioProprioResponse {

        return usuarioCrudService.buscarPorAuthSub(auth.attributes["sub"] as String)
            .let { UsuarioProprioResponse.from(it) }
    }
}