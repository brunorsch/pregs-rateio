package br.app.pregsrateio.common.erros;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static br.app.pregsrateio.common.erros.ErroResponse.INESPERADO;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ControleFluxoException.class)
    public ResponseEntity<ErroResponse> handleControleFluxoException(ControleFluxoException ex) {
        log.debug("Erro de negócio: [{}]", ex.getMessage());

        ErroResponse response = ErroResponse.builder()
                .codigo(ex.getCodigo())
                .mensagem(ex.getMensagem())
                .detalhes(ex.getDetalhes())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErroResponse response = ErroResponse.builder()
                .codigo(FORBIDDEN.name())
                .mensagem("Você não está autorizado a acessar este recurso.")
                .build();

        return ResponseEntity.status(FORBIDDEN).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErroResponse> handleAuthenticationException(AuthenticationException ex) {
        ErroResponse response = ErroResponse.builder()
                .codigo(UNAUTHORIZED.name())
                .mensagem("Autentique-se para continuar")
                .build();

        return ResponseEntity.status(UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleGenericException(Exception ex) {
        log.error("Erro Inesperado: [{}]", ex.getMessage(), ex);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(INESPERADO);
    }
}
