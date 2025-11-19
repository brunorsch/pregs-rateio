package br.app.pregsrateio.rateio.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;

import javax.annotation.Nullable;

import org.bson.types.ObjectId;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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
import br.app.pregsrateio.common.security.UsuarioLogado;
import br.app.pregsrateio.common.validation.MongoIdValido;
import br.app.pregsrateio.rateio.service.CrudRateioService;
import br.app.pregsrateio.rateio.controller.dto.AtualizacaoRateioRequest;
import br.app.pregsrateio.rateio.controller.dto.CadastroRateioRequest;
import br.app.pregsrateio.rateio.controller.dto.RateioProprioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rateios")
@Tag(name = "Rateios", description = "Gestão e cadastro de rateios")
@Validated
@RequiredArgsConstructor
@MDCUsuarioId
@SecurityRequirement(name = "jwt")
public class RateioController {
    private final CrudRateioService crudRateioService;

    @PostMapping
    @PreAuthorize("permitAll()")
    @ResponseStatus(CREATED)
    @Operation(
        summary = "Cadastrar rateio",
        description = "Cria um novo rateio. O serviço permite a criação de rateio autenticado ou anônimo."
    )
    @ApiResponse(responseCode = "201", description = "Rateio criado com sucesso")
    public RateioProprioResponse postRateio(
        @Valid @RequestBody CadastroRateioRequest request,
        @Nullable UsuarioLogado principal) {

        var cadastrado = principal == null
            ? crudRateioService.cadastrarAnonimo(request)
            : crudRateioService.cadastrarParaUsuario(principal, request);

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
        @PageableDefault(sort = "historico.dataCriacao", direction = DESC) @ParameterObject Pageable pageable,
        UsuarioLogado user) {
        System.out.println(user.getPrincipal());
        var page = crudRateioService.listarPorUsuario(
            user.getUsuario().getId(), pageable);

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
        @MongoIdValido @PathVariable("id") String rateioId,
        UsuarioLogado user) {

        var rateio = crudRateioService.buscarPorId(
            user.getIdUsuario(), new ObjectId(rateioId));

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
        @MongoIdValido @PathVariable("id") String rateioId,
        @Valid @RequestBody AtualizacaoRateioRequest request,
        UsuarioLogado user) {
        var rateioAtualizado = crudRateioService.atualizar(
            user.getIdUsuario(), new ObjectId(rateioId), request);

        return RateioProprioResponse.fromDomain(rateioAtualizado);
    }
}
