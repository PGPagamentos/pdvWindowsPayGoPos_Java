package br.com.paygo.enums;

import java.util.Arrays;

/**
 * Tipos de dados que podem ser retornados pela automação como status final para uma transação
 */
public enum PTICnf {
    SUCCESS(1), // Transação confirmada.
    PRINTERR(2), // Erro na impressora, desfazer a transação
    DISPFAIL(3), // Erro com o mecanismo dispensador, desfazer a transação
    OTHERERR(4); // Outro erro, desfazer a transação.

    private final int value;

    PTICnf(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PTICnf valueOf(int value) throws Exception {
        return Arrays.stream(values()).filter(ptiCnf -> ptiCnf.value == value).findFirst().orElseThrow(() -> new Exception("Tipo de dado de confirmação de transação não mapeado (" + value + ")."));
    }
}
