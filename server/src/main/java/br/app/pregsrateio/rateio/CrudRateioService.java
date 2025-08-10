package br.app.pregsrateio.rateio;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import org.springframework.stereotype.Service;

import br.app.pregsrateio.common.erros.ControleFluxoException;
import br.app.pregsrateio.common.security.UsuarioPrincipal;
import br.app.pregsrateio.rateio.api.dto.CadastroRateioRequest;
import br.app.pregsrateio.rateio.data.Rateio;
import br.app.pregsrateio.rateio.data.RateioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrudRateioService {
    private final RateioRepository rateioRepository;

    public Rateio cadastrar(UsuarioPrincipal principal, CadastroRateioRequest request) {
        log.info("Cadastrando novo rateio: [{}] para usuário: [{}]",
            request.getNome(), principal.getSuid());

        validarCamposCriacao(request);

        var novoRateio = Rateio.criar(request, principal.getUsuario().getId());

        var rateioCriado = rateioRepository.save(novoRateio);

        log.info("Rateio cadastrado com sucesso: [{}] com ID: [{}]",
            rateioCriado.getNome(), rateioCriado.getId());

        return novoRateio;
    }

    private void validarCamposCriacao(CadastroRateioRequest request) {
        if(isNull(request.getValorTotal()) && isEmpty(request.getItens())) {
            log.warn("Erro de validação ao cadastrar rateio. Valor total e itens nulos ou vazios.");
            throw new ControleFluxoException(
                "VALIDACAO", "Valor total ou itens devem ser informados para o rateio.");
        }
    }
}
