package tools.challenge.domain.pagamentos.transacao.usecases.pagamento;

import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.core.usecases.EmptyValueOutput;
import tools.challenge.core.usecases.UseCase;
import tools.challenge.core.usecases.ValueOutput;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;

import java.util.Optional;

public class BuscarPagamentoPorIdUseCase implements UseCase<TransacaoIdInput, ValueOutput> {

    private TransacaoRepository repository;

    public BuscarPagamentoPorIdUseCase(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Either<Error, ValueOutput> execute(final TransacaoIdInput input) {
        return Either.attempt(() -> buscaPagamento(input));
    }

    private ValueOutput buscaPagamento(final TransacaoIdInput input) {
        final Optional<Transacao> transacao = repository.findByIdTransacaoDePagamento(input.toTransacaoId());
        if (transacao.isEmpty()) return EmptyValueOutput.create();
        return TransacaoOutput.criaOutput(transacao.get());
    }
}
