package br.app.pregsrateio.rateio;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.app.pregsrateio.common.erros.ControleFluxoException;
import br.app.pregsrateio.common.security.UsuarioLogado;
import br.app.pregsrateio.rateio.api.dto.AtualizacaoAmigoRequest;
import br.app.pregsrateio.rateio.api.dto.CadastroAmigoRequest;
import br.app.pregsrateio.rateio.data.Amigo;
import br.app.pregsrateio.rateio.data.AmigoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrudAmigoService {
    private final AmigoRepository amigoRepository;

    public Amigo cadastrarParaUsuario(UsuarioLogado usuario, CadastroAmigoRequest request) {
        log.info("Cadastrando novo amigo: [{}]", request);

        Amigo amigo = Amigo.builder().build();

        log.info("Amigo cadastrado com sucesso: [{}]", amigo.getId());

        return amigoRepository.save(amigo);
    }

    public Page<Amigo> listarPorUsuario(UsuarioLogado usuario, Pageable pageable) {
        var idUsuario = usuario.getIdUsuario();
        log.debug("Listando amigos do usuário: [{}]", idUsuario);

        return amigoRepository.findAllByOrganizadorId(idUsuario, pageable);
    }

    public Amigo buscarPorIdParaUsuario(UsuarioLogado usuario, ObjectId amigoId) {
        log.debug("Buscando amigo por ID: [{}] do usuário: [{}]", amigoId, usuario.getIdUsuario());

        return amigoRepository.findByIdAndOrganizadorId(amigoId, usuario.getIdUsuario())
            .orElseThrow(() -> new ControleFluxoException("AMIGO_NAO_ENCONTRADO", "Amigo não encontrado", NOT_FOUND));
    }

    public Amigo atualizarParaUsuario(UsuarioLogado usuario, AtualizacaoAmigoRequest request) {
        var amigoId = new ObjectId(request.id());
        var usuarioId = usuario.getIdUsuario();
        log.info("Atualizando amigo: [{}] do usuário: [{}]", amigoId, usuarioId);

        var amigoExistente = buscarPorIdParaUsuario(usuario, amigoId);

        

        log.info("Amigo atualizado com sucesso: [{}]", amigoExistente.getId());

        return amigoRepository.save(amigoExistente);
    }
}
