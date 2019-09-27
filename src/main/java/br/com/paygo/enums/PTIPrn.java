package br.com.paygo.enums;

import java.util.Arrays;

public enum PTIPrn {
    MERCHANT(1),  // Via do estabelecimento
    CHOLDER(2);  // Via do portador do cartão

    private final int value;

    PTIPrn(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PTIPrn valueOf(int value) throws Exception {
        return Arrays.stream(values()).filter(ptiPrn -> ptiPrn.value == value).findFirst().orElseThrow(() -> new Exception("Tipo de dado de impressão não mapeado (" + value + ")."));
    }
}
