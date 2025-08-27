package br.app.pregsrateio.rateio.api;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;

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
import br.app.pregsrateio.rateio.CrudAmigoService;
import br.app.pregsrateio.rateio.api.dto.AmigoProprioResponse;
import br.app.pregsrateio.rateio.api.dto.AtualizacaoAmigoRequest;
import br.app.pregsrateio.rateio.api.dto.AtualizacaoRateioRequest;
import br.app.pregsrateio.rateio.api.dto.CadastroAmigoRequest;
import br.app.pregsrateio.rateio.api.dto.RateioProprioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/amigos")
@Tag(name = "Amigos", description = "Gestão e cadastro de amigos. Os amigos "
    + "representam as pessoas que participam dos rateios junto com o organizador.")
@Validated
@RequiredArgsConstructor
@MDCUsuarioId
@SecurityRequirement(name = "jwt")
public class AmigoController {
    private final CrudAmigoService crudAmigoService;

    @PostMapping
    @PreAuthorize("hasRole('PGRT_USER')")
    @ResponseStatus(CREATED)
    @Operation(
        summary = "Cadastrar amigo",
        description = "Cadastra um amigo no sistema."
    )
    @ApiResponse(responseCode = "201", description = "Rateio criado com sucesso")
    public AmigoProprioResponse postAmigo(
        @Valid @RequestBody CadastroAmigoRequest request,
        UsuarioLogado principal) {
        var cadastrado = crudAmigoService.cadastrarParaUsuario(principal, request);

        return AmigoProprioResponse.fromDomain(cadastrado);
    }

    @GetMapping
    @PreAuthorize("hasRole('PGRT_USER')")
    @Operation(
        summary = "Listar amigos próprios",
        description = "Lista os amigos do usuário autenticado."
    )
    @ApiResponse(responseCode = "200", description = "Amigos listados com sucesso")
    public Page<AmigoProprioResponse> getAmigos(
        @PageableDefault(sort = "historico.dataCriacao", direction = DESC) @ParameterObject Pageable pageable,
        UsuarioLogado user) {
        System.out.println(user.getPrincipal());
        var page = crudAmigoService.listarPorUsuario(user, pageable);

        return page.map(AmigoProprioResponse::fromDomain);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PGRT_USER')")
    @Operation(
        summary = "Consultar um amigo por ID",
        description = "Consulta os detalhes de um amigo pelo ID. Para exibir, é necessário que o usuário seja o "
            + "detentor do cadastro."
    )
    @ApiResponse(responseCode = "200", description = "Detalhes do amigo obtidos com sucesso")
    @ApiResponse(responseCode = "404", description = "Amigo não encontrado")
    public AmigoProprioResponse getAmigoPorId(
        @MongoIdValido @PathVariable("id") String amigoId, UsuarioLogado user) {

        var amigo = crudAmigoService.buscarPorIdParaUsuario(user, new ObjectId(amigoId));

        return AmigoProprioResponse.fromDomain(amigo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PGRT_USER')")
    @Operation(
        summary = "Atualizar um amigo próprio",
        description = "Atualiza os detalhes de um amigo específico criado pelo usuário autenticado."
    )
    @ApiResponse(responseCode = "200", description = "Amigo atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Amigo não encontrado")
    public AmigoProprioResponse putAmigo(
        @Valid @RequestBody AtualizacaoAmigoRequest request,
        UsuarioLogado user) {
        var amigoAtualizado = crudAmigoService.atualizarParaUsuario(
            user, request);

        return AmigoProprioResponse.fromDomain(amigoAtualizado);
    }
}
