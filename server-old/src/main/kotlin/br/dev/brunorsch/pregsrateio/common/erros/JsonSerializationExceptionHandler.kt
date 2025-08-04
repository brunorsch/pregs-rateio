package br.dev.brunorsch.pregsrateio.common.erros

import br.dev.brunorsch.pregsrateio.common.log
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
        log().error("Erro de serialização: [{}]", exception.message, exception)
        
        val response = ErroResponse.INESPERADO

        return HttpResponse.serverError(response)
    }
}
