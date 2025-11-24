package tools.challenge.domain.pagamentos.usecases;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.domain.pagamentos.shared.cartaodecredito.error.CartaoDeCreditoError;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.error.DescricaoOperacaoError;
import tools.challenge.domain.pagamentos.shared.formadepagamento.error.FormaDePagamentoError;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoStatus;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;
import tools.challenge.domain.pagamentos.transacao.usecases.input.DescricaoOperacaoTransacaoInput;
import tools.challenge.domain.pagamentos.transacao.usecases.input.FormaDePagamentoInput;
import tools.challenge.domain.pagamentos.transacao.usecases.input.TransacaoInput;
import tools.challenge.domain.pagamentos.transacao.usecases.output.TransacaoOutput;
import tools.challenge.domain.pagamentos.transacao.usecases.pagamento.RealizarPagamentoUseCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static tools.challenge.factories.ObjectFactoryTest.criaCartaoDeCredito;

@QuarkusTest
public class RealizarPagamentoUseCaseTest {

    @Inject
    TransacaoRepository repository;

    @Inject
    RealizarPagamentoUseCase useCase;

    @Test
    void deveSerPossivelRealizarPagamento() {
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String dataHoraFormatada = dataHora.format(DateTimeFormatter
                .ofPattern("dd/MM/yyyy HH:mm:ss"));
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final String tipoFormaPagamento = "AVISTA";
        final String numeroCartao = criaCartaoDeCredito().numero();
        final int parcelas = 1;
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertNotNull(output);
        assertTrue(output.isSuccess());
        assertNotNull(output.getSuccess().id());
        assertNotNull(output.getSuccess().descricaoOperacao().nsu());
        assertEquals(valor, output.getSuccess().descricaoOperacao().valor());
        assertEquals(dataHoraFormatada, output.getSuccess().descricaoOperacao().dataHora());
        assertEquals(estabelecimento, output.getSuccess().descricaoOperacao().estabelecimento());
        assertEquals(TransacaoStatus.AUTORIZADO.toString(), output.getSuccess().descricaoOperacao().status());
        assertEquals(tipoFormaPagamento, output.getSuccess().formaPagamento().tipo());
        assertEquals(parcelas, output.getSuccess().formaPagamento().parcelas());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComEstabelecimentoNull() {
        final String mensagemErroEsperada = "Descrição de operação deve possuir um nome de até 255 caracteres.";
        final String typeError = DescricaoOperacaoError.typeError();
        final LocalDateTime dataHora = LocalDateTime.now();
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final String tipoFormaPagamento = "AVISTA";
        final String numeroCartao = criaCartaoDeCredito().numero();
        final int parcelas = 1;
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, null, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComEstabelecimentoVazio() {
        final String mensagemErroEsperada = "Descrição de operação deve possuir um nome de até 255 caracteres.";
        final String typeError = DescricaoOperacaoError.typeError();
        final LocalDateTime dataHora = LocalDateTime.now();
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final String tipoFormaPagamento = "AVISTA";
        final String numeroCartao = criaCartaoDeCredito().numero();
        final int parcelas = 1;
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, "", dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComEstabelecimentoComNomeMaisQue255Caracteres() {
        final String mensagemErroEsperada = "Descrição de operação deve possuir um nome de até 255 caracteres.";
        final String typeError = DescricaoOperacaoError.typeError();
        final String estabelecimento = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Aenean pulvinar lacus a tellus volutpat maximus. Fusce hendrerit odio " +
                "vitae leo varius vulputate. Curabitur sed ligula sit amet urna condimentum " +
                "semper. Praesent quis urna erat. In a eleifend magna. Interdum et malesuada fames ac ante i" +
                "psum primis in faucibus. Curabitur quis risus ligula. Quisque quis facilisis lorem, id varius metus. " +
                "Etiam dapibus dictum nulla, ac blandit neque malesuada ac. Quisque finibus turpis vitae urna semper" +
                " ornare. Praesent congue placerat sapien quis mollis. Integer eu tempus enim, id faucibus lacus. Mauris" +
                " ornare varius sagittis. Vestibulum finibus neque sit amet risus aliquam, eu malesuada urna placerat.";
        final LocalDateTime dataHora = LocalDateTime.now();
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final String tipoFormaPagamento = "AVISTA";
        final String numeroCartao = criaCartaoDeCredito().numero();
        final int parcelas = 1;
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComValorNull() {
        final String mensagemErroEsperada = "Descrição de operação deve possuir um valor válido.";
        final String typeError = DescricaoOperacaoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String tipoFormaPagamento = "AVISTA";
        final String numeroCartao = criaCartaoDeCredito().numero();
        final int parcelas = 1;
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(null, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComDataHoraNull() {
        final String mensagemErroEsperada = "Descrição de operação deve possuir uma data e hora válida.";
        final String typeError = DescricaoOperacaoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final String tipoFormaPagamento = "AVISTA";
        final String numeroCartao = criaCartaoDeCredito().numero();
        final int parcelas = 1;
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, null);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComFormaDePagamentoNull() {
        final String mensagemErroEsperada = "Tipo de forma de pagamento deve ser AVISTA, PARCELADO LOJA ou PARCELADO EMISSOR.";
        final String typeError = FormaDePagamentoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String numeroCartao = criaCartaoDeCredito().numero();
        final int parcelas = 1;
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(null, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComFormaDePagamentoVazio() {
        final String mensagemErroEsperada = "Tipo de forma de pagamento deve ser AVISTA, PARCELADO LOJA ou PARCELADO EMISSOR.";
        final String typeError = FormaDePagamentoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String numeroCartao = criaCartaoDeCredito().numero();
        final int parcelas = 1;
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput("", parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComFormaDePagamentoComValorNaoMapeado() {
        final String mensagemErroEsperada = "Tipo de forma de pagamento deve ser AVISTA, PARCELADO LOJA ou PARCELADO EMISSOR.";
        final String typeError = FormaDePagamentoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String numeroCartao = criaCartaoDeCredito().numero();
        final int parcelas = 1;
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput("naoMapeado", parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComFormaDePagamentoComParcelaMenorQue0() {
        final String mensagemErroEsperada = "Forma de pagamento deve possuir quantidade de parcelas maior que 0.";
        final String typeError = FormaDePagamentoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String numeroCartao = criaCartaoDeCredito().numero();
        final String tipoFormaPagamento = "AVISTA";
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, -1);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComFormaDePagamentoComParcelaComValor0() {
        final String mensagemErroEsperada = "Forma de pagamento deve possuir quantidade de parcelas maior que 0.";
        final String typeError = FormaDePagamentoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String numeroCartao = criaCartaoDeCredito().numero();
        final String tipoFormaPagamento = "AVISTA";
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, 0);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComCartaoDeCreditoNull() {
        final String mensagemErroEsperada = "Cartão de crédito deve possuir um número de 16 dígitos.";
        final String typeError = CartaoDeCreditoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String tipoFormaPagamento = "AVISTA";
        final int parcelas = 1;
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput(null, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComCartaoDeCreditoComValorVazio() {
        final String mensagemErroEsperada = "Cartão de crédito deve possuir um número de 16 dígitos.";
        final String typeError = CartaoDeCreditoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String tipoFormaPagamento = "AVISTA";
        final int parcelas = 1;
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput("", descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComCartaoDeCreditoComValorMenorQue16Digitos() {
        final String mensagemErroEsperada = "Cartão de crédito deve possuir um número de 16 dígitos.";
        final String typeError = CartaoDeCreditoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String tipoFormaPagamento = "AVISTA";
        final int parcelas = 1;
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput("125489", descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComCartaoDeCreditoComValorMaiorQue16Digitos() {
        final String mensagemErroEsperada = "Cartão de crédito deve possuir um número de 16 dígitos.";
        final String typeError = CartaoDeCreditoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String tipoFormaPagamento = "AVISTA";
        final int parcelas = 1;
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput("606282884182006711445", descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }

    @Test
    void deveSerPossivelRealizarPagamentoComCartaoDeCreditoComValorQualquer() {
        final String mensagemErroEsperada = "Cartão de crédito deve possuir um número de 16 dígitos.";
        final String typeError = CartaoDeCreditoError.typeError();
        final String estabelecimento = "PetShop Mundo Cão";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String tipoFormaPagamento = "AVISTA";
        final int parcelas = 1;
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .criaInput(tipoFormaPagamento, parcelas);
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .criaInput(valor, estabelecimento, dataHora);
        final TransacaoInput input = TransacaoInput
                .criaInput("606te6a1x1v!@#$6", descricaoOperacaoTransacaoInput, formaDePagamentoInput);
        final Either<Error, TransacaoOutput> output = useCase.execute(input);
        assertTrue(output.isError());
        assertNotNull(output.getError());
        assertEquals(typeError, output.getError().getTypeError());
        assertEquals(mensagemErroEsperada, output.getError().getMessage());
    }
}
