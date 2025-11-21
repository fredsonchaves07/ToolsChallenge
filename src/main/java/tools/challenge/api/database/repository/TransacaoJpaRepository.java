package tools.challenge.api.database.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import tools.challenge.api.database.entity.TransacaoJpa;
import tools.challenge.domain.pagamentos.transacao.entities.TipoTransacao;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoID;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TransacaoJpaRepository implements TransacaoRepository {

    @Override
    public List<Transacao> transacoesPagamento() {
        final List<TransacaoJpa> transacaoJpaList = TransacaoJpa
                .find("tipoTransacao = ?1", TipoTransacao.PAGAMENTO.toString())
                .list();
        return transacaoJpaList.stream().map(TransacaoJpa::toAggregate).toList();
    }

    @Override
    public Optional<Transacao> findByIdTransacaoDePagamento(final TransacaoID id) {
        if (id == null) {
            return Optional.empty();
        }
        final Optional<TransacaoJpa> transacaoJpa = (TransacaoJpa.find(
                        "tipoTransacao = ?1 and id =?2", TipoTransacao.PAGAMENTO.toString(), id.toString())
                .firstResultOptional()
                .stream()
                .map(transacao -> (TransacaoJpa) transacao))
                .findFirst();
        return transacaoJpa.map(TransacaoJpa::toAggregate);
    }

    @Override
    public List<Transacao> transacoesEstorno() {
        final List<TransacaoJpa> transacaoJpaList = TransacaoJpa
                .find("tipoTransacao = ?1", TipoTransacao.ESTORNO.toString())
                .list();
        return transacaoJpaList.stream().map(TransacaoJpa::toAggregate).toList();
    }

    @Override
    public Optional<Transacao> findByIdTransacaoDeEstorno(final TransacaoID id) {
        if (id == null) {
            return Optional.empty();
        }
        final Optional<TransacaoJpa> transacaoJpa = (TransacaoJpa.find(
                        "tipoTransacao = ?1 and id =?2", TipoTransacao.ESTORNO.toString(), id.toString())
                .firstResultOptional()
                .stream()
                .map(transacao -> (TransacaoJpa) transacao))
                .findFirst();
        return transacaoJpa.map(TransacaoJpa::toAggregate);
    }

    @Override
    @Transactional
    public void save(final Transacao entity) {
        if (entity == null) return;
        final TransacaoJpa transacaoJpa = TransacaoJpa.from(entity);
        transacaoJpa.persist();
    }

    @Override
    public Optional<Transacao> findById(final TransacaoID id) {
        if (id == null) return Optional.empty();
        final Optional<TransacaoJpa> transacao = TransacaoJpa.findByIdOptional(id.toString());
        return transacao.map(TransacaoJpa::toAggregate);
    }

    @Override
    public List<Transacao> findAll() {
        final List<TransacaoJpa> transacaoJpaList = TransacaoJpa.findAll().list();
        return transacaoJpaList.stream().map(TransacaoJpa::toAggregate).toList();
    }

    @Override
    @Transactional
    public void delete(final Transacao entity) {
        if (entity == null) return;
        final Optional<TransacaoJpa> transacaoJpa = TransacaoJpa.findByIdOptional(entity.id().toString());
        transacaoJpa.ifPresent(TransacaoJpa::delete);
    }

    @Override
    @Transactional
    public void deleteAll() {
        TransacaoJpa.deleteAll();
    }
}
