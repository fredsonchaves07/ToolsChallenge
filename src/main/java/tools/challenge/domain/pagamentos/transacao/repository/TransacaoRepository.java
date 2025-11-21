package tools.challenge.domain.pagamentos.transacao.repository;

import tools.challenge.core.repository.Repository;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoID;

import java.util.List;
import java.util.Optional;

public interface TransacaoRepository extends Repository<TransacaoID, Transacao> {

    List<Transacao> transacoesPagamento();

    Optional<Transacao> findByIdTransacaoDePagamento(final TransacaoID id);

    List<Transacao> transacoesEstorno();

    Optional<Transacao> findByIdTransacaoDeEstorno(final TransacaoID id);

}
