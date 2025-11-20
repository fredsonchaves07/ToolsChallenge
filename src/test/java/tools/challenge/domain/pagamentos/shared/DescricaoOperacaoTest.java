package tools.challenge.domain.pagamentos.shared;

import org.junit.jupiter.api.Test;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.error.DescricaoOperacaoError;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class DescricaoOperacaoTest {

    @Test
    void deveSerPossivelCriarUmaDescricaoDeOperacao() {
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final LocalDateTime dataHora = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        final String estabelecimento = "PetShop Mundo Cão";
        final String dataHoraFormatada = dataHora.format(DateTimeFormatter
                .ofPattern("dd/MM/yyyy hh:mm:ss", Locale.of("pt-BR")));
        final DescricaoOperacao descricao = DescricaoOperacao.criaDescricaoOperacao(estabelecimento, valor, dataHora);
        assertNotNull(descricao);
        assertEquals(valor, descricao.valor());
        assertEquals(dataHora, descricao.dataHora());
        assertEquals(dataHoraFormatada, descricao.dataHoraFormatada());
        assertEquals(estabelecimento, descricao.estabelecimento());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmaDescricaoDeOperacaoComNomeNull() {
        final String mensagemDeErroEsperada = "Descrição de operação deve possuir um nome de até 255 caracteres.";
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final LocalDateTime dataHora = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        final String typeError = DescricaoOperacaoError.typeError();
        final DescricaoOperacaoError descricaoOperacaoError = assertThrows(
                DescricaoOperacaoError.class,
                () -> DescricaoOperacao.criaDescricaoOperacao(null, valor, dataHora));
        assertEquals(typeError, descricaoOperacaoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, descricaoOperacaoError.getMessage());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmaDescricaoDeOperacaoComNomeVazio() {
        final String mensagemDeErroEsperada = "Descrição de operação deve possuir um nome de até 255 caracteres.";
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final LocalDateTime dataHora = LocalDateTime.now();
        final String estabelecimento = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Aenean pulvinar lacus a tellus volutpat maximus. Fusce hendrerit odio " +
                "vitae leo varius vulputate. Curabitur sed ligula sit amet urna condimentum " +
                "semper. Praesent quis urna erat. In a eleifend magna. Interdum et malesuada fames ac ante i" +
                "psum primis in faucibus. Curabitur quis risus ligula. Quisque quis facilisis lorem, id varius metus. " +
                "Etiam dapibus dictum nulla, ac blandit neque malesuada ac. Quisque finibus turpis vitae urna semper" +
                " ornare. Praesent congue placerat sapien quis mollis. Integer eu tempus enim, id faucibus lacus. Mauris" +
                " ornare varius sagittis. Vestibulum finibus neque sit amet risus aliquam, eu malesuada urna placerat.";
        final String typeError = DescricaoOperacaoError.typeError();
        final DescricaoOperacaoError descricaoOperacaoError = assertThrows(
                DescricaoOperacaoError.class,
                () -> DescricaoOperacao.criaDescricaoOperacao(estabelecimento, valor, dataHora));
        assertEquals(typeError, descricaoOperacaoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, descricaoOperacaoError.getMessage());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmaDescricaoDeOperacaoComValorNull() {
        final String mensagemDeErroEsperada = "Descrição de operação deve possuir um valor válido.";
        final LocalDateTime dataHora = LocalDateTime.now();
        final String estabelecimento = "PetShop Mundo Cão";
        final String typeError = DescricaoOperacaoError.typeError();
        final DescricaoOperacaoError descricaoOperacaoError = assertThrows(
                DescricaoOperacaoError.class,
                () -> DescricaoOperacao.criaDescricaoOperacao(estabelecimento, null, dataHora));
        assertEquals(typeError, descricaoOperacaoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, descricaoOperacaoError.getMessage());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmaDescricaoDeOperacaoComDataHoraNull() {
        final String mensagemDeErroEsperada = "Descrição de operação deve possuir uma data e hora válida.";
        final BigDecimal valor = BigDecimal.valueOf(500.50);
        final String estabelecimento = "PetShop Mundo Cão";
        final String typeError = DescricaoOperacaoError.typeError();
        final DescricaoOperacaoError descricaoOperacaoError = assertThrows(
                DescricaoOperacaoError.class,
                () -> DescricaoOperacao.criaDescricaoOperacao(estabelecimento, valor, null));
        assertEquals(typeError, descricaoOperacaoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, descricaoOperacaoError.getMessage());
    }
}
