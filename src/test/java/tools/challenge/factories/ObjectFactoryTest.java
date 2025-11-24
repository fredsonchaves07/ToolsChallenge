package tools.challenge.factories;

import net.datafaker.Faker;
import tools.challenge.domain.pagamentos.shared.cartaodecredito.valueobject.CartaoDeCredito;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacao;
import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.FormaDePagamento;
import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.TipoFormaDePagamento;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static net.datafaker.providers.base.Finance.CreditCardType.MASTERCARD;

public final class ObjectFactoryTest {

    private static final Faker faker = new Faker(Locale.of("pt-BR"));

    public static CartaoDeCredito criaCartaoDeCredito() {
        return CartaoDeCredito.criaCartaoDeCredito(faker.finance()
                .creditCard(MASTERCARD)
                .replaceAll("-", ""));
    }

    public static DescricaoOperacao criaDescricaoOperacao() {
        final String estabelecimento = faker.company().name();
        final BigDecimal valor = new BigDecimal(faker.commerce().price().replaceAll(",", "."));
        return DescricaoOperacao.criaDescricaoOperacao(estabelecimento, valor, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS));
    }

    public static FormaDePagamento criaFormaDePagamentoAVista() {
        return FormaDePagamento.criaFormaDePagamento(TipoFormaDePagamento.AVISTA, 1);
    }

    public static Transacao criaTransacaoDePagamento() {
        return Transacao.criaTransacaoDePagamento(
                criaDescricaoOperacao(),
                criaCartaoDeCredito(),
                criaFormaDePagamentoAVista());
    }

    public static Transacao criaTransacaoDeEstorno() {
        return Transacao.criaTransacaoDeEstorno(
                criaDescricaoOperacao(),
                criaCartaoDeCredito(),
                criaFormaDePagamentoAVista());
    }
}
