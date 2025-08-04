package br.app.pregsrateio.usuario.api;

import br.app.pregsrateio.usuario.CrudUsuarioService;
import br.app.pregsrateio.usuario.api.dto.CadastroUsuarioRequest;
import br.app.pregsrateio.usuario.api.dto.UsuarioProprioResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Gestão e cadastro dos usuários da aplicação (Organizadores)")
@Validated
@RequiredArgsConstructor
public class UsuarioController {
    private final CrudUsuarioService usuarioCrudService;

    @GetMapping("/completude")
    @PreAuthorize("hasRole('PGRT_USER')")
    @Operation(
            summary = "Verificar se o cadastro do usuário está completo",
            description = "Verifica se o usuário possui todos os dados necessários " +
                    "para ser considerado completo. O endpoint pode ser usado para " +
                    "verificar se o usuário recem cadastrado no Auth0 precisa passar pelo " +
                    "fluxo de onboarding"
    )
    @ApiResponse(responseCode = "200", description = "Cadastro regularizado")
    @ApiResponse(responseCode = "412", description = "Cadastro incompleto")
    public ResponseEntity<Void> getCompletude(@AuthenticationPrincipal Jwt jwt) {
        var sub = jwt.getClaimAsString("sub");
        var isCompleto = usuarioCrudService.validarCadastroCompleto(sub);

        return ResponseEntity.status(isCompleto ? OK : PRECONDITION_FAILED).build();
    }

    @PostMapping
    @PreAuthorize("hasRole('PGRT_USER')")
    @ResponseStatus(CREATED)
    @Operation(
            summary = "Cadastrar usuário",
            description = "Realiza o cadastro de um novo usuário na aplicação. O usuário " +
                    "precisa ter sua conta já criada no Auth0 para concluir o cadastro."
    )
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    public UsuarioProprioResponse postUsuario(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CadastroUsuarioRequest request) {
        var sub = jwt.getClaimAsString("sub");
        return UsuarioProprioResponse.from(usuarioCrudService.cadastrar(sub, request));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('PGRT_USER')")
    @Operation(
            summary = "Buscar dados do usuário logado",
            description = "Busca os dados do usuário logado na aplicação. " +
                    "O usuário precisa estar autenticado e o token JWT deve ser " +
                    "enviado no header Authorization."
    )
    @ApiResponse(responseCode = "200", description = "Dados do usuário encontrados")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public UsuarioProprioResponse getUsuarioLogado(@AuthenticationPrincipal Jwt jwt) {
        var sub = jwt.getClaimAsString("sub");
        return UsuarioProprioResponse.from(usuarioCrudService.buscarPorAuthSub(sub));
    }
}