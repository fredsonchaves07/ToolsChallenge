package tools.challenge.api.config.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;
import tools.challenge.domain.pagamentos.transacao.usecases.estorno.BuscarEstornoPorIdUseCase;
import tools.challenge.domain.pagamentos.transacao.usecases.estorno.BuscarTodosEstornosUseCase;
import tools.challenge.domain.pagamentos.transacao.usecases.estorno.RealizarEstornoUseCase;

@ApplicationScoped
public class EstornoUseCaseConfig {

    @Inject
    TransacaoRepository repository;

    @Inject
    RealizarEstornoUseCase realizarEstornoUseCase;

    @Inject
    BuscarEstornoPorIdUseCase buscarEstornoPorIdUseCase;

    @Inject
    BuscarTodosEstornosUseCase buscarTodosEstornosUseCase;

    @ApplicationScoped
    public RealizarEstornoUseCase realizarEstornoUseCase() {
        return new RealizarEstornoUseCase(repository);
    }

    @ApplicationScoped
    public BuscarEstornoPorIdUseCase buscarEstornoPorIdUseCase() {
        return new BuscarEstornoPorIdUseCase(repository);
    }

    @ApplicationScoped
    public BuscarTodosEstornosUseCase buscarTodosEstornosUseCase() {
        return new BuscarTodosEstornosUseCase(repository);
    }
}
