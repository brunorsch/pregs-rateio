package br.app.pregsrateio.rateio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.app.pregsrateio.common.security.UsuarioLogado;
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

    public Amigo cadastrar(UsuarioLogado usuario, CadastroAmigoRequest request) {
        log.info("Cadastrando novo amigo: [{}]", request);

        Amigo amigo = Amigo.builder().build();

        log.info("Amigo cadastrado com sucesso: [{}]", amigo.getId());

        return amigoRepository.save(amigo);
    }

    public Page<Amigo> listarPorUsuario(UsuarioLogado usuario, Pageable pageable) {
        log.debug("Listando amigos do usuário: [{}]", usuario.getIdUsuario());

        return amigoRepository.findAll(pageable);
    }
}
