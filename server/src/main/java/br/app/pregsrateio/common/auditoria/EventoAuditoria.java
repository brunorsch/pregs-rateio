package br.app.pregsrateio.common.auditoria;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public record EventoAuditoria(
    String tipo,
    String descricao,
    OffsetDateTime data,
    String usuario,
    Map<String, Object> detalhes
) {
    public EventoAuditoria(String tipo, String usuario) {
        this(tipo, null, usuario);
    }

    public EventoAuditoria(String tipo, String descricao, String usuario) {
        this(tipo, descricao, usuario, new HashMap<>());
    }

    public EventoAuditoria(String tipo, String descricao, String usuario, Map<String, Object> detalhes) {
        this(tipo, descricao, OffsetDateTime.now(), usuario, new HashMap<>(detalhes));
    }
}