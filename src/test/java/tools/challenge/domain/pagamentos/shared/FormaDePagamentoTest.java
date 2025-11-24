package tools.challenge.domain.pagamentos.shared;

import org.junit.jupiter.api.Test;
import tools.challenge.domain.pagamentos.shared.formadepagamento.error.FormaDePagamentoError;
import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.FormaDePagamento;
import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.TipoFormaDePagamento;

import static org.junit.jupiter.api.Assertions.*;

public class FormaDePagamentoTest {

    @Test
    void deveSerPossivelCriarUmaFormaDePagamento() {
        final TipoFormaDePagamento tipoFormaDePagamento = TipoFormaDePagamento.AVISTA;
        final int quantidadeParcelas = 1;
        FormaDePagamento formaDePagamento = FormaDePagamento
                .criaFormaDePagamento(tipoFormaDePagamento, quantidadeParcelas);
        assertNotNull(formaDePagamento);
        assertEquals(tipoFormaDePagamento, formaDePagamento.tipoFormaDePagamento());
        assertEquals(quantidadeParcelas, formaDePagamento.quantidadeParcelas());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmaFormaDePagamentoComTipoNull() {
        final String mensagemDeErroEsperada = "Forma de pagamento deve ser do tipo AVISTA, " +
                "PARCELADO LOJA ou PARCELADO EMISSOR.";
        final int quantidadeDeParcelas = 1;
        final String typeError = FormaDePagamentoError.typeError();
        final FormaDePagamentoError formaDePagamentoError = assertThrows(
                FormaDePagamentoError.class,
                () -> FormaDePagamento.criaFormaDePagamento(null, quantidadeDeParcelas));
        assertEquals(typeError, formaDePagamentoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, formaDePagamentoError.getMessage());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmaFormaDePagamentoComQuantidadeDeParcelasNegativa() {
        final String mensagemDeErroEsperada = "Forma de pagamento deve possuir quantidade de parcelas maior que 0.";
        final TipoFormaDePagamento tipoFormaDePagamento = TipoFormaDePagamento.PARCELADO_LOJA;
        final int quantidadeParcelas = -1;
        final String typeError = FormaDePagamentoError.typeError();
        final FormaDePagamentoError formaDePagamentoError = assertThrows(
                FormaDePagamentoError.class,
                () -> FormaDePagamento.criaFormaDePagamento(tipoFormaDePagamento, quantidadeParcelas));
        assertEquals(typeError, formaDePagamentoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, formaDePagamentoError.getMessage());
    }
}
