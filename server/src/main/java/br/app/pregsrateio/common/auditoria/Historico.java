package br.app.pregsrateio.common.auditoria;

import static lombok.AccessLevel.PRIVATE;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe representando o histórico de ações ou eventos no sistema.
 * Pode ser utilizada para registrar dados de auditoria.
 * Objetiva ser utilizada como parte de um documento persistido em base MongoDB.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Historico {
    @CreatedDate
    private OffsetDateTime dataCriacao;

    @CreatedBy
    private String usuarioCriacao;

    @LastModifiedDate
    private OffsetDateTime dataUltimaAlteracao;

    @LastModifiedBy
    private String usuarioUltimaAlteracao;

    @Setter(PRIVATE)
    private Set<EventoAuditoria> historico = null;

    public void pushHistorico(EventoAuditoria evento) {
        if(historico == null) {
            setHistorico(new HashSet<>());
        }

        historico.add(evento);
    }
}
