package tools.challenge.domain.pagamentos.transacao.usecases.estorno;

import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.core.usecases.EmptyValueOutput;
import tools.challenge.core.usecases.UseCase;
import tools.challenge.core.usecases.ValueOutput;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;
import tools.challenge.domain.pagamentos.transacao.usecases.input.TransacaoIdInput;
import tools.challenge.domain.pagamentos.transacao.usecases.output.TransacaoOutput;

import java.util.Optional;

public class BuscarEstornoPorIdUseCase implements UseCase<TransacaoIdInput, ValueOutput> {

    private TransacaoRepository repository;

    public BuscarEstornoPorIdUseCase(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Either<Error, ValueOutput> execute(final TransacaoIdInput input) {
        return Either.attempt(() -> buscarEstorno(input));
    }

    private ValueOutput buscarEstorno(final TransacaoIdInput input) {
        final Optional<Transacao> transacao = repository.findByIdTransacaoDeEstorno(input.toTransacaoId());
        if (transacao.isEmpty()) return EmptyValueOutput.create();
        return TransacaoOutput.criaOutput(transacao.get());
    }
}
