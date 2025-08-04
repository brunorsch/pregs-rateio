package br.app.pregsrateio.common.erros;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Data
@EqualsAndHashCode(callSuper = true)
public class ControleFluxoException extends RuntimeException {
    private final String codigo;
    private final String mensagem;
    private final List<Map<String, Object>> detalhes;
    private final HttpStatus status;

    public ControleFluxoException(String codigo, String mensagem) {
        this(codigo, mensagem, Collections.emptyList(), UNPROCESSABLE_ENTITY);
    }

    public ControleFluxoException(String codigo, String mensagem, HttpStatus status) {
        this(codigo, mensagem, Collections.emptyList(), status);
    }

    public ControleFluxoException(String codigo, String mensagem, List<Map<String, Object>> detalhes, HttpStatus status) {
        super("Erro de controle de fluxo: " + codigo, null, true, false);
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.detalhes = detalhes != null ? detalhes : Collections.emptyList();
        this.status = status != null ? status : UNPROCESSABLE_ENTITY;
    }
}

