package br.dev.brunorsch.pregsrateio

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
	info = Info(
		title = "PregsRateio",
		version = "1.0",
		description = "Serviços do back-end do PregsRateio"
	)
)
object ApplicationKt {
	@JvmStatic
	fun main(args: Array<String>) {
		run(*args)
	}
}
