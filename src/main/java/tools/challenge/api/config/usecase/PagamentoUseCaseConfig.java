package tools.challenge.api.config.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;
import tools.challenge.domain.pagamentos.transacao.usecases.pagamento.RealizarPagamentoUseCase;

@ApplicationScoped
public class PagamentoUseCaseConfig {

    @Inject
    TransacaoRepository repository;

    @Inject
    RealizarPagamentoUseCase realizarPagamentoUseCase;

    @ApplicationScoped
    public RealizarPagamentoUseCase realizarPagamentoUseCase() {
        return new RealizarPagamentoUseCase(repository);
    }
}
