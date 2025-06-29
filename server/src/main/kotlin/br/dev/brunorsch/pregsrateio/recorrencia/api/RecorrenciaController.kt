package br.dev.brunorsch.pregsrateio.recorrencia.api

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(
    name = "Recorrências",
    description = "Gestão das recorrências para rateio de gastos recorrentes"
)
@Controller("/recorrencias")
open class RecorrenciaController {
    @Get("/test")
    open fun test(): String {
        return "Hello World";
    }
}