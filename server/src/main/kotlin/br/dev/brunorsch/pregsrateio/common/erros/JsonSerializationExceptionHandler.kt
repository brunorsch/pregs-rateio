package br.dev.brunorsch.pregsrateio.common.erros

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.serde.exceptions.SerdeException
import jakarta.inject.Singleton

@Singleton
class JsonSerializationExceptionHandler : ExceptionHandler<SerdeException, HttpResponse<ErroResponse>> {
    override fun handle(
        request: HttpRequest<*>,
        exception: io.micronaut.serde.exceptions.SerdeException
    ): HttpResponse<ErroResponse> {
        val response = ErroResponse(
            codigo = "SERIALIZATION_ERROR",
            mensagem = "Erro ao serializar a resposta"
        )
        return HttpResponse.serverError(response)
    }
}