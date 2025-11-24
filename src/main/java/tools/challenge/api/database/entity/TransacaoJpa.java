package tools.challenge.api.database.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tools.challenge.domain.pagamentos.shared.cartaodecredito.valueobject.CartaoDeCredito;
import tools.challenge.domain.pagamentos.shared.codigoautorizacao.CodigoAutorizacao;
import tools.challenge.domain.pagamentos.shared.codigoautorizacao.CodigoNSU;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacao;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacaoTransacao;
import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.FormaDePagamento;
import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.TipoFormaDePagamento;
import tools.challenge.domain.pagamentos.transacao.entities.TipoTransacao;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoID;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACAO")
public class TransacaoJpa extends PanacheEntityBase {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TIPO_TRANSACAO", nullable = false)
    private String tipoTransacao;

    @Column(name = "NUMERO_CARTAO", nullable = false)
    private String numeroCartao;

    @Column(name = "VALOR", nullable = false)
    private BigDecimal valor;

    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "ESTABELECIMENTO", nullable = false)
    private String estabelecimento;

    @Column(name = "CODIGO_NSU", nullable = false)
    private String codigoNSU;

    @Column(name = "CODIGO_AUTORIZACAO", nullable = false)
    private String codigoAutorizacao;

    @Column(name = "FORMA_DE_PAGAMENTO", nullable = false)
    private String formaDePagamento;

    @Column(name = "PARCELAS")
    private int parcelas;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Deprecated
    private TransacaoJpa() {
    }

    private TransacaoJpa(final Transacao transacao) {
        this.id = transacao.id().toString();
        this.tipoTransacao = transacao.tipoTransacao().toString();
        this.codigoAutorizacao = transacao.descricaoOperacao().codigoAutorizacao().toString();
        this.codigoNSU = transacao.descricaoOperacao().nsu().toString();
        this.numeroCartao = transacao.cartaoDeCredito().numero();
        this.valor = transacao.descricaoOperacao().valor();
        this.dataHora = transacao.descricaoOperacao().descricaoOperacao().dataHora();
        this.estabelecimento = transacao.descricaoOperacao().estabelecimento();
        this.formaDePagamento = transacao.formaDePagamento().tipoFormaDePagamento().name();
        this.parcelas = transacao.formaDePagamento().quantidadeParcelas();
        this.status = transacao.descricaoOperacao().status().toString();
        this.createdAt = transacao.createdAt();
        this.updatedAt = transacao.updatedAt();
    }

    public static TransacaoJpa from(final Transacao transacao) {
        return new TransacaoJpa(transacao);
    }

    public Transacao toAggregate() {
        if (tipoTransacaoPagamento())
            return Transacao.criaTransacaoDePagamento(
                    TransacaoID.of(id), descricaoOperacaoTransacao(), cartaoDeCredito(), formaDePagamento(), createdAt, updatedAt);
        return Transacao.criaTransacaoDeEstorno(
                TransacaoID.of(id), descricaoOperacaoTransacao(), cartaoDeCredito(), formaDePagamento(), createdAt, updatedAt);
    }

    private FormaDePagamento formaDePagamento() {
        return FormaDePagamento.criaFormaDePagamento(TipoFormaDePagamento.valueOf(formaDePagamento), parcelas);
    }

    private CartaoDeCredito cartaoDeCredito() {
        return CartaoDeCredito.criaCartaoDeCredito(numeroCartao);
    }

    private DescricaoOperacaoTransacao descricaoOperacaoTransacao() {
        return DescricaoOperacaoTransacao.of(codigoNSU(), codigoAutorizacao(), transacaoStatus(), descricaoOperacao());
    }

    final DescricaoOperacao descricaoOperacao() {
        return DescricaoOperacao.criaDescricaoOperacao(estabelecimento, valor, dataHora);
    }

    private TransacaoStatus transacaoStatus() {
        return TransacaoStatus.valueOf(status);
    }

    private CodigoAutorizacao codigoAutorizacao() {
        return CodigoAutorizacao.of(codigoAutorizacao);
    }

    private CodigoNSU codigoNSU() {
        return CodigoNSU.of(codigoNSU);
    }

    private boolean tipoTransacaoPagamento() {
        return TipoTransacao.valueOf(tipoTransacao).equals(TipoTransacao.PAGAMENTO);
    }
}
