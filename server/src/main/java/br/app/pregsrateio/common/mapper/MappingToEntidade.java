package br.app.pregsrateio.common.mapper;

import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Retention;

import org.mapstruct.Mapping;

/**
 * Anotação utilitária para usar em entidades.
 * Seta os campos ID (Gerado pelo banco) e histórico (Utilizado por padrão um
 * objeto novo para cada entidade, recurso commons do app) como ignorados
 * no target.
 */
@Retention(CLASS)
@Mapping(target = "id", ignore = true)
@Mapping(target = "historico", ignore = true)
public @interface MappingToEntidade {}
