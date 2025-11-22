package tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject;

import tools.challenge.core.validator.Validator;
import tools.challenge.core.valueobject.ValueObject;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.error.DescricaoOperacaoError;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record DescricaoOperacao(String estabelecimento, BigDecimal valor,
                                LocalDateTime dataHora) implements ValueObject {

    public static DescricaoOperacao criaDescricaoOperacao(
            final String estabelecimento, final BigDecimal valor, final LocalDateTime dataHora) {
        return new DescricaoOperacao(estabelecimento, valor, dataHora).validate();
    }

    public static DescricaoOperacao criaDescricaoOperacaoComFormatoDataHoraString(final String estabelecimento,
                                                      final BigDecimal valor,
                                                      final String dataHora) {
        try {
            final LocalDateTime dataHoraLocalDate = LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            return new DescricaoOperacao(estabelecimento, valor, dataHoraLocalDate).validate();
        } catch (Exception exception) {
            throw DescricaoOperacaoError.erro("Formato de data / hora inválido. Verifique se segue o formato: dd/MM/yyyy HH:mm:ss");
        }
    }

    @Override
    public DescricaoOperacao validate() {
        Validator.create(DescricaoOperacaoError::erro)
                .rule(this::estabelecimentoEInvalido,
                        "Descrição de operação deve possuir um nome de até 255 caracteres.")
                .rule(this::valorEInvalido,
                        "Descrição de operação deve possuir um valor válido.")
                .rule(this::dataHoraEInvalido,
                        "Descrição de operação deve possuir uma data e hora válida.")
                .validate();
        return this;
    }

    private boolean estabelecimentoEInvalido() {
        return estabelecimento == null || estabelecimento.isBlank() || estabelecimento.length() > 255;
    }

    private boolean valorEInvalido() {
        return valor == null;
    }

    private boolean dataHoraEInvalido() {
        return dataHora == null;
    }

    public String dataHoraFormatada() {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss", Locale.of("pt-BR")));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final DescricaoOperacao that = (DescricaoOperacao) o;
        return valor.equals(that.valor) && estabelecimento.equals(that.estabelecimento) && dataHora.equals(that.dataHora);
    }

    @Override
    public int hashCode() {
        int result = estabelecimento.hashCode();
        result = 31 * result + valor.hashCode();
        result = 31 * result + dataHora.hashCode();
        return result;
    }
}
