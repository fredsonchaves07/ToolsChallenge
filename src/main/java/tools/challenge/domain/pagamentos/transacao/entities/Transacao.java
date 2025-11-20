package tools.challenge.domain.pagamentos.transacao.entities;

import tools.challenge.core.entity.Entity;
import tools.challenge.core.error.Error;
import tools.challenge.core.validator.Validator;
import tools.challenge.domain.pagamentos.shared.cartaodecredito.valueobject.CartaoDeCredito;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacao;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacaoTransacao;
import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.FormaDePagamento;
import tools.challenge.domain.pagamentos.transacao.error.TransacaoError;

import java.time.LocalDateTime;

public class Transacao extends Entity<TransacaoID> {

    private final DescricaoOperacaoTransacao descricaoOperacao;

    private final CartaoDeCredito cartaoDeCredito;

    private final FormaDePagamento formaDePagamento;

    private final TipoTransacao tipo;

    public Transacao(final DescricaoOperacaoTransacao descricaoOperacao, final CartaoDeCredito cartaoDeCredito,
                     final FormaDePagamento formaDePagamento, final TipoTransacao tipo) {
        super(TransacaoID.of());
        this.descricaoOperacao = descricaoOperacao;
        this.cartaoDeCredito = cartaoDeCredito;
        this.formaDePagamento = formaDePagamento;
        this.tipo = tipo;
    }

    public Transacao(final TransacaoID id, final LocalDateTime createdAt, final LocalDateTime updatedAt,
                     final DescricaoOperacaoTransacao descricaoOperacao, final CartaoDeCredito cartaoDeCredito,
                     final FormaDePagamento formaDePagamento, final TipoTransacao tipo) {
        super(id, createdAt, updatedAt);
        this.descricaoOperacao = descricaoOperacao;
        this.cartaoDeCredito = cartaoDeCredito;
        this.formaDePagamento = formaDePagamento;
        this.tipo = tipo;
    }

    public static Transacao criaTransacaoDePagamento(final DescricaoOperacao descricaoOperacao,
                                                     final CartaoDeCredito cartaoDeCredito,
                                                     final FormaDePagamento formaDePagamento) {
        final DescricaoOperacaoTransacao descricaoOperacaoTransacao = DescricaoOperacaoTransacao
                .of(descricaoOperacao, TransacaoStatus.AUTORIZADO);
        return new Transacao(descricaoOperacaoTransacao, cartaoDeCredito, formaDePagamento, TipoTransacao.PAGAMENTO)
                .validate();
    }

    public static Transacao criaTransacaoDePagamento(final TransacaoID id,
                                                     final DescricaoOperacaoTransacao descricaoOperacaoTransacao,
                                                     final CartaoDeCredito cartaoDeCredito,
                                                     final FormaDePagamento formaDePagamento,
                                                     final LocalDateTime createdAt,
                                                     final LocalDateTime updatedAt) {
        return new Transacao(id, createdAt, updatedAt, descricaoOperacaoTransacao, cartaoDeCredito, formaDePagamento, TipoTransacao.PAGAMENTO)
                .validate();
    }

    public static Transacao criaTransacaoDeEstorno(final DescricaoOperacao descricaoOperacao,
                                                   final CartaoDeCredito cartaoDeCredito,
                                                   final FormaDePagamento formaDePagamento) {
        final DescricaoOperacaoTransacao descricaoOperacaoTransacao = DescricaoOperacaoTransacao
                .of(descricaoOperacao, TransacaoStatus.NEGADO);
        return new Transacao(descricaoOperacaoTransacao, cartaoDeCredito, formaDePagamento, TipoTransacao.ESTORNO)
                .validate();
    }

    public static Transacao criaTransacaoDeEstorno(final TransacaoID id,
                                                   final DescricaoOperacaoTransacao descricaoOperacaoTransacao,
                                                   final CartaoDeCredito cartaoDeCredito,
                                                   final FormaDePagamento formaDePagamento,
                                                   final LocalDateTime createdAt,
                                                   final LocalDateTime updatedAt) {
        return new Transacao(id, createdAt, updatedAt, descricaoOperacaoTransacao, cartaoDeCredito, formaDePagamento, TipoTransacao.ESTORNO)
                .validate();
    }

    public DescricaoOperacaoTransacao descricaoOperacao() {
        return descricaoOperacao;
    }

    public CartaoDeCredito cartaoDeCredito() {
        return cartaoDeCredito;
    }

    public FormaDePagamento formaDePagamento() {
        return formaDePagamento;
    }

    @Override
    protected Transacao validate() throws Error {
        Validator.create(TransacaoError::erro)
                .rule(this::descricaoOperacaoENulo,
                        "Transação deve possuir a descrição da operação.")
                .rule(this::cartaoDeCreditoENulo,
                        "Transação deve possuir o número do cartão de crédito.")
                .rule(this::formaDePagamentoENulo,
                        "Transação deve possuir a forma de pagamento.")
                .validate();
        return this;
    }

    private boolean descricaoOperacaoENulo() {
        return descricaoOperacao == null;
    }

    private boolean cartaoDeCreditoENulo() {
        return cartaoDeCredito == null;
    }

    private boolean formaDePagamentoENulo() {
        return formaDePagamento == null;
    }

    public TipoTransacao tipoTransacao() {
        return tipo;
    }
}
