package br.app.pregsrateio.common.auditoria;

import static java.util.Objects.requireNonNullElse;
import static java.util.stream.Collectors.toSet;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoricoResponse {
    private OffsetDateTime dataCriacao;
    private String usuarioCriacao;
    private OffsetDateTime dataUltimaAlteracao;
    private String usuarioUltimaAlteracao;
    @Builder.Default
    private Set<EventoAuditoriaResponse> eventos = null;

    public static HistoricoResponse fromDomain(Historico historico) {
        return HistoricoResponse.builder()
            .dataCriacao(historico.getDataCriacao())
            .usuarioCriacao(historico.getUsuarioCriacao())
            .dataUltimaAlteracao(historico.getDataUltimaAlteracao())
            .usuarioUltimaAlteracao(historico.getUsuarioUltimaAlteracao())
            .eventos(new HashSet<>(
                requireNonNullElse(
                    historico.getEventos(),
                    new HashSet<EventoAuditoria>())
                    .stream()
                    .map(EventoAuditoriaResponse::fromDomain)
                    .collect(toSet())))
            .build();
    }

    public record EventoAuditoriaResponse(
        String tipo,
        String descricao,
        OffsetDateTime data,
        String usuario,
        Map<String, Object> detalhes
    ) {
        public static EventoAuditoriaResponse fromDomain(EventoAuditoria evento) {
            return new EventoAuditoriaResponse(
                evento.tipo(),
                evento.descricao(),
                evento.data(),
                evento.usuario(),
                Map.copyOf(evento.detalhes())
            );
        }
    }
}
