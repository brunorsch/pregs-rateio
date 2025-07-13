package br.dev.brunorsch.pregsrateio.common.erros

import br.dev.brunorsch.pregsrateio.common.log
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus.FORBIDDEN
import io.micronaut.http.HttpStatus.UNAUTHORIZED
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Produces
import io.micronaut.security.authentication.AuthorizationException
import io.swagger.v3.oas.annotations.Hidden
import jakarta.inject.Singleton


@Controller("/error")
@Hidden
@Produces
@Singleton
class CustomExceptionHandler {
    @Error(global = true)
    fun handleControleFluxoError(
        request: HttpRequest<*>,
        exception: ControleFluxoException
    ): HttpResponse<ErroResponse> {
        log().debug("Erro de negócio: [{}]", exception.message)

        val response = ErroResponse(
            codigo = exception.codigo,
            mensagem = exception.mensagem,
            detalhes = exception.detalhes
        )

        return HttpResponse.status<ErroResponse>(exception.status)
            .body(response)
    }

    @Error(global = true)
    fun handleAuthorizationException(
        request: HttpRequest<*>?,
        exception: AuthorizationException,
    ): HttpResponse<ErroResponse> {
        val isForbidden = exception.isForbidden

        val response = ErroResponse(
            codigo =
                if(isForbidden) "FORBIDDEN"
                else "UNAUTHORIZED",
            mensagem =
                if (isForbidden) "Você não está autorizado a acessar este recurso."
                else "Autentique-se para continuar",
        )

        return HttpResponse
            .status<ErroResponse>(
                if (isForbidden) FORBIDDEN
                else UNAUTHORIZED
            )
            .body(response)
    }

    @Error(global = true)
    fun handleGenericError(request: HttpRequest<*>?, ex: Throwable): HttpResponse<ErroResponse> {
        log().error("Erro Inesperado: [{}]", ex.message, ex)

        return HttpResponse.serverError(ErroResponse.INESPERADO)
    }
}