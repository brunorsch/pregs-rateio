package br.app.pregsrateio.rateio.mapper;

@Mapper
public interface AmigoMapper {
    Amigo toDomainUpdate(AtualizacaoAmigoRequest request, @MappingTarget Amigo amigo);
}
