package br.dev.brunorsch.pregsrateio.recorrencia

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/recorrencias")
open class RecorrenciaController {
    @Get("/test")
    open fun test(): String {
        return "Hello World";
    }
}