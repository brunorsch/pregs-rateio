package br.app.pregsrateio.rateio.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.app.pregsrateio.common.mapper.MappingToEntidade;
import br.app.pregsrateio.common.mapper.ObjectIdStringMapper;
import br.app.pregsrateio.common.security.UsuarioLogado;
import br.app.pregsrateio.rateio.api.dto.AtualizacaoAmigoRequest;
import br.app.pregsrateio.rateio.api.dto.CadastroAmigoRequest;
import br.app.pregsrateio.rateio.data.Amigo;

@Mapper(uses = ObjectIdStringMapper.class)
public interface AmigoMapper {
    @Mapping(target = "historico", ignore = true)
    @Mapping(target = "organizadorId", ignore = true)
    void updateFromRequest(@MappingTarget Amigo amigo, AtualizacaoAmigoRequest request);

    @MappingToEntidade
    @Mapping(source = "usuarioLogado.idUsuario", target = "organizadorId")
    Amigo fromCadastroRequest(CadastroAmigoRequest request, UsuarioLogado usuarioLogado);
}
