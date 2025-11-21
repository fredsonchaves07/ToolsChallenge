package tools.challenge.domain.pagamentos.transacao.usecases.pagamento;

import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.core.usecases.ListUseCase;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;

public class BuscarTodosPagamentosUseCase implements ListUseCase<ListTransacaoOutput> {

    private TransacaoRepository repository;

    public BuscarTodosPagamentosUseCase(final TransacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Either<Error, ListTransacaoOutput> execute() {
        return Either.attempt(this::buscaPagamentos);
    }

    private ListTransacaoOutput buscaPagamentos() {
        return ListTransacaoOutput.criaOutput(repository.transacoesPagamento());
    }
}
