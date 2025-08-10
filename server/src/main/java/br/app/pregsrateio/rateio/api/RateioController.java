package br.app.pregsrateio.rateio.api;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.app.pregsrateio.common.aop.annotations.MDCUsuarioId;
import br.app.pregsrateio.common.security.UsuarioPrincipal;
import br.app.pregsrateio.rateio.CrudRateioService;
import br.app.pregsrateio.rateio.api.dto.CadastroRateioRequest;
import br.app.pregsrateio.rateio.api.dto.RateioProprioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rateios")
@Tag(name = "Rateios", description = "Gestão e cadastro de rateios")
@Validated
@RequiredArgsConstructor
@MDCUsuarioId
public class RateioController {
    private final CrudRateioService crudRateioService;

    @PostMapping
    @PreAuthorize("hasRole('PGRT_USER')")
    @ResponseStatus(CREATED)
    @Operation(
        summary = "Cadastrar rateio",
        description = "Cria um novo rateio."
    )
    @ApiResponse(responseCode = "201", description = "Rateio criado com sucesso")
    public RateioProprioResponse postRateio(
        @AuthenticationPrincipal UsuarioPrincipal principal,
        @Valid @RequestBody CadastroRateioRequest request) {

        var cadastrado = crudRateioService.cadastrar(principal, request);

        return RateioProprioResponse.fromDomain(cadastrado);
    }
}
