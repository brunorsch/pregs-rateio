package br.app.pregsrateio.common.erros;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static br.app.pregsrateio.common.erros.ErroResponse.INESPERADO;
import static java.util.Objects.requireNonNullElse;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @ExceptionHandler
    public ResponseEntity<ErroResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.debug("Erro de validação: [{}]", ex.getMessage());

        var violacoes = ex.getFieldErrors();
        var mensagem = ex.getFieldErrorCount() == 1
            ? violacoes.getFirst().getDefaultMessage()
            : "Multiplos erros de validação";

        var erroResponse = ErroResponse.builder()
                .codigo("VALIDACAO")
                .mensagem(mensagem)
                .detalhes(violacoes.stream()
                    .map(violacao -> Map.of(
                        "campo", violacao.getField(),
                        "mensagem", (Object) requireNonNullElse(violacao.getDefaultMessage(), "Inválido")))
                    .toList())
                .build();

        return ResponseEntity.badRequest().body(erroResponse);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.debug("Erro de constraint: [{}]", ex.getMessage());

        var violacoes = new ArrayList<>(ex.getConstraintViolations());
        var mensagem = violacoes.size() == 1 ? violacoes.getFirst().getMessage(): "Multiplos erros de validação";

        ErroResponse response = ErroResponse.builder()
                .codigo("VALIDACAO")
                .mensagem(mensagem)
                .detalhes(violacoes.stream()
                    .map(cv -> Map.of(
                        "campo", cv.getPropertyPath().toString(),
                        "mensagem", (Object) cv.getMessage()))
                    .toList())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleGenericException(Exception ex) {
        log.error("Erro Inesperado: [{}]", ex.getMessage(), ex);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(INESPERADO);
    }
}
