package br.app.pregsrateio.rateio.service;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.math.BigDecimal;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.app.pregsrateio.common.erros.ControleFluxoException;
import br.app.pregsrateio.common.security.UsuarioLogado;
import br.app.pregsrateio.rateio.controller.dto.AtualizacaoRateioRequest;
import br.app.pregsrateio.rateio.controller.dto.CadastroRateioRequest;
import br.app.pregsrateio.rateio.data.Rateio;
import br.app.pregsrateio.rateio.data.RateioRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrudRateioService {
    private final RateioRepository rateioRepository;

    public Rateio cadastrarAnonimo(CadastroRateioRequest request) {
        log.info("Cadastrando rateio com usuário anônimo: [{}]", request.nome());

        return cadastrar(null, request);
    }

    public Rateio cadastrarParaUsuario(UsuarioLogado principal, CadastroRateioRequest request) {
        log.info("Cadastrando novo rateio: [{}] para usuário: [{}]",
            request.nome(), principal.getIdUsuario());

        return cadastrar(principal.getIdUsuario(), request);
    }

    private Rateio cadastrar(@Nullable ObjectId usuarioId, CadastroRateioRequest request) {
        validarCamposCriacao(request.valorTotal(), request.itens());

        var novoRateio = request.toDomain(usuarioId);

        var rateioCriado = rateioRepository.save(novoRateio);

        log.info("Rateio cadastrado com sucesso com ID: [{}]", rateioCriado.getId());

        return rateioCriado;
    }

    private void validarCamposCriacao(BigDecimal valorTotal, List<?> itens) {
        if(isNull(valorTotal) && isEmpty(itens)) {
            log.warn("Erro de validação ao cadastrar rateio. Valor total e itens nulos ou vazios.");
            throw new ControleFluxoException(
                "VALIDACAO", "Valor total ou itens devem ser informados para a criação do rateio.");
        }
    }

    public Page<Rateio> listarPorUsuario(ObjectId usuarioId, Pageable pageable) {
        log.debug("Listando rateios para usuário: [{}]", usuarioId);

        return rateioRepository.findAllByUsuarioId(usuarioId, pageable);
    }

    public Rateio buscarPorId(ObjectId usuarioId, ObjectId rateioId) {
        log.debug("Buscando rateio por ID: [{}]", rateioId);

        return rateioRepository.findByIdAndUsuarioId(rateioId, usuarioId)
            .orElseThrow(() -> new ControleFluxoException(
                "RATEIO_NAO_ENCONTRADO", "Rateio não encontrado", NOT_FOUND));
    }

    public Rateio atualizar(ObjectId usuarioId, ObjectId rateioId, AtualizacaoRateioRequest request) {
        log.info("Atualizando rateio: [{}] para usuário: [{}]", rateioId, usuarioId);

        var rateioExistente = buscarPorId(usuarioId, rateioId);

        validarCamposCriacao(request.getValorTotal(), request.getItens());

        request.atualizarDominio(rateioExistente);

        ofNullable(request.getItens())
            .ifPresent(ignored -> rateioExistente.calcularValorTotalItens());

        rateioExistente.registrarAtualizacao(usuarioId);

        return rateioRepository.save(rateioExistente);
    }
}
