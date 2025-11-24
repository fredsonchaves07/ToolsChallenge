package tools.challenge.domain.pagamentos.transacao.usecases.estorno;

import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.core.usecases.ListUseCase;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;
import tools.challenge.domain.pagamentos.transacao.usecases.output.ListTransacaoOutput;

public class BuscarTodosEstornosUseCase implements ListUseCase<ListTransacaoOutput> {

    private TransacaoRepository repository;

    public BuscarTodosEstornosUseCase(final TransacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Either<Error, ListTransacaoOutput> execute() {
        return Either.attempt(this::buscarEstornos);
    }

    private ListTransacaoOutput buscarEstornos() {
        return ListTransacaoOutput.criaOutput(repository.transacoesEstorno());
    }
}
