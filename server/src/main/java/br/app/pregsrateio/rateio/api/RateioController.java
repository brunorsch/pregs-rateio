package br.app.pregsrateio.rateio.api;

import static org.springframework.http.HttpStatus.CREATED;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.app.pregsrateio.common.aop.annotations.MDCUsuarioId;
import br.app.pregsrateio.common.security.UsuarioPrincipal;
import br.app.pregsrateio.rateio.CrudRateioService;
import br.app.pregsrateio.rateio.api.dto.AtualizacaoRateioRequest;
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

    @GetMapping
    @PreAuthorize("hasRole('PGRT_USER')")
    @Operation(
        summary = "Listar rateios próprios",
        description = "Lista os rateios criados pelo usuário autenticado."
    )
    @ApiResponse(responseCode = "200", description = "Rateios listados com sucesso")
    public Page<RateioProprioResponse> getRateios(
        @AuthenticationPrincipal UsuarioPrincipal principal,
        @PageableDefault(sort = "historico.dataCriacao") Pageable pageable) {

        var page = crudRateioService.listarPorUsuario(
            principal.getUsuario().getId(), pageable);

        return page.map(RateioProprioResponse::fromDomain);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PGRT_USER')")
    @Operation(
        summary = "Detalhar rateio próprio",
        description = "Obtém os detalhes de um rateio específico criado pelo usuário autenticado."
    )
    @ApiResponse(responseCode = "200", description = "Detalhes do rateio obtidos com sucesso")
    public RateioProprioResponse getRateioPorId(
        @AuthenticationPrincipal UsuarioPrincipal principal,
        @PathVariable("id") String rateioId) {

        var rateio = crudRateioService.buscarPorId(
            principal.getIdUsuario(), new ObjectId(rateioId));

        return RateioProprioResponse.fromDomain(rateio);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PGRT_USER')")
    @Operation(
        summary = "Atualizar rateio próprio",
        description = "Atualiza os detalhes de um rateio específico criado pelo usuário autenticado."
    )
    @ApiResponse(responseCode = "200", description = "Rateio atualizado com sucesso")
    public RateioProprioResponse putRateio(
        @AuthenticationPrincipal UsuarioPrincipal principal,
        @PathVariable("id") String rateioId,
        @Valid @RequestBody AtualizacaoRateioRequest request) {
        var rateioAtualizado = crudRateioService.atualizar(
            principal.getIdUsuario(), new ObjectId(rateioId), request);

        return RateioProprioResponse.fromDomain(rateioAtualizado);
    }
}
