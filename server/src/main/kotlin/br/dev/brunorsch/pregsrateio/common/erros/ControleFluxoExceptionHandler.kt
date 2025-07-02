package br.dev.brunorsch.pregsrateio.common.erros

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [ControleFluxoException::class, ExceptionHandler::class])
class ControleFluxoExceptionHandler
    : ExceptionHandler<ControleFluxoException, HttpResponse<ErroResponse>> {
    override fun handle(
        request: HttpRequest<*>,
        exception: ControleFluxoException
    ): HttpResponse<ErroResponse> {
        val response = ErroResponse(
            codigo = exception.codigo,
            mensagem = exception.mensagem,
            detalhes = exception.detalhes
        )

        return HttpResponse.status<ErroResponse>(exception.status)
            .body(response)
    }
}