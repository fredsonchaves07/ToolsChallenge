package tools.challenge.domain.pagamentos.transacao.usecases.pagamento;

import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.core.usecases.UseCase;
import tools.challenge.domain.pagamentos.shared.cartaodecredito.valueobject.CartaoDeCredito;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;

public class RealizarPagamentoUseCase implements UseCase<TransacaoInput, TransacaoOutput> {

    private TransacaoRepository repository;

    public RealizarPagamentoUseCase(final TransacaoRepository transacaoRepository) {
        this.repository = transacaoRepository;
    }

    @Override
    public Either<Error, TransacaoOutput> execute(final TransacaoInput input) {
        return Either.attempt(() -> criaPagamento(input));
    }

    private TransacaoOutput criaPagamento(final TransacaoInput input) {
        final Transacao transacao = Transacao.criaTransacaoDePagamento(
                input.descricaoOperacao().toAggregate(),
                CartaoDeCredito.criaCartaoDeCredito(input.numeroCartao()),
                input.formaPagamento().toAggregate());
        repository.save(transacao);
        return TransacaoOutput.criaInput(transacao);
    }
}
