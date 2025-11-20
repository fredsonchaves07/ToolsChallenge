package tools.challenge.domain.pagamentos.transacao.repository;

import tools.challenge.core.repository.Repository;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoID;

import java.util.List;

public interface TransacaoRepository extends Repository<TransacaoID, Transacao> {

    List<Transacao> transacoesPagamento();

    List<Transacao> transacoesEstorno();
}
