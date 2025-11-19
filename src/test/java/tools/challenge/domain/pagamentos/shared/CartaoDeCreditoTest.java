package tools.challenge.domain.pagamentos.shared;

import org.junit.jupiter.api.Test;
import tools.challenge.domain.pagamentos.shared.cartaodecredito.error.CartaoDeCreditoError;
import tools.challenge.domain.pagamentos.shared.cartaodecredito.valueobject.CartaoDeCredito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.wildfly.common.Assert.assertNotNull;

public class CartaoDeCreditoTest {

    @Test
    void deveSerPossivelCadastrarUmCartaoDeCredito() {
        final String numero = "6062828841820067";
        final CartaoDeCredito cartaoDeCredito = CartaoDeCredito.criaCartaoDeCredito(numero);
        assertNotNull(cartaoDeCredito);
        assertEquals(numero, cartaoDeCredito.numero());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmCartaoDeCreditoComNumeroNull() {
        final String mensagemDeErroEsperada = "Cartão de crédito deve possuir um número de 16 dígitos.";
        final String typeError = CartaoDeCreditoError.typeError();
        final CartaoDeCreditoError cartaoDeCreditoError = assertThrows(
                CartaoDeCreditoError.class,
                () -> CartaoDeCredito.criaCartaoDeCredito(null));
        assertEquals(typeError, cartaoDeCreditoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, cartaoDeCreditoError.getMessage());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmCartaoDeCreditoComNumeroVazio() {
        final String mensagemDeErroEsperada = "Cartão de crédito deve possuir um número de 16 dígitos.";
        final String typeError = CartaoDeCreditoError.typeError();
        final CartaoDeCreditoError cartaoDeCreditoError = assertThrows(
                CartaoDeCreditoError.class,
                () -> CartaoDeCredito.criaCartaoDeCredito(""));
        assertEquals(typeError, cartaoDeCreditoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, cartaoDeCreditoError.getMessage());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmCartaoDeCreditoComNumeroMenorQue16Digitos() {
        final String mensagemDeErroEsperada = "Cartão de crédito deve possuir um número de 16 dígitos.";
        final String typeError = CartaoDeCreditoError.typeError();
        final CartaoDeCreditoError cartaoDeCreditoError = assertThrows(
                CartaoDeCreditoError.class,
                () -> CartaoDeCredito.criaCartaoDeCredito("125489"));
        assertEquals(typeError, cartaoDeCreditoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, cartaoDeCreditoError.getMessage());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmCartaoDeCreditoComNumeroMaiorQue16Digitos() {
        final String mensagemDeErroEsperada = "Cartão de crédito deve possuir um número de 16 dígitos.";
        final String typeError = CartaoDeCreditoError.typeError();
        final CartaoDeCreditoError cartaoDeCreditoError = assertThrows(
                CartaoDeCreditoError.class,
                () -> CartaoDeCredito.criaCartaoDeCredito("606282884182006711445"));
        assertEquals(typeError, cartaoDeCreditoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, cartaoDeCreditoError.getMessage());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmCartaoDeCreditoComNumeroComCaracterQualquer() {
        final String mensagemDeErroEsperada = "Cartão de crédito deve possuir um número de 16 dígitos.";
        final String typeError = CartaoDeCreditoError.typeError();
        final CartaoDeCreditoError cartaoDeCreditoError = assertThrows(
                CartaoDeCreditoError.class,
                () -> CartaoDeCredito.criaCartaoDeCredito("606te6a1x1v!@#$6"));
        assertEquals(typeError, cartaoDeCreditoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, cartaoDeCreditoError.getMessage());
    }
}
