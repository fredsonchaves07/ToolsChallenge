package tools.challenge.domain.pagamentos.transacao.usecases.estorno;

import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.core.usecases.UseCase;
import tools.challenge.domain.pagamentos.shared.cartaodecredito.valueobject.CartaoDeCredito;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;
import tools.challenge.domain.pagamentos.transacao.usecases.input.TransacaoInput;
import tools.challenge.domain.pagamentos.transacao.usecases.output.TransacaoOutput;

public class RealizarEstornoUseCase implements UseCase<TransacaoInput, TransacaoOutput> {

    private TransacaoRepository repository;

    public RealizarEstornoUseCase(final TransacaoRepository transacaoRepository) {
        this.repository = transacaoRepository;
    }

    @Override
    public Either<Error, TransacaoOutput> execute(final TransacaoInput input) {
        return Either.attempt(() -> criaEstorno(input));
    }

    private TransacaoOutput criaEstorno(final TransacaoInput input) {
        final Transacao transacao = Transacao.criaTransacaoDeEstorno(
                input.descricaoOperacao().toAggregate(),
                CartaoDeCredito.criaCartaoDeCredito(input.numeroCartao()),
                input.formaPagamento().toAggregate());
        repository.save(transacao);
        return TransacaoOutput.criaOutput(transacao);
    }
}
