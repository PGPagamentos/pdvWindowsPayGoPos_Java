package br.com.paygo.enums;

/**
 * Tipos de operações disponibilizadas
 */
public enum PWOper {
    ADMIN((short)32),      // Qualquer transação que não seja um pagamento(estorno, pré-autorização, consulta, relatório, reimpressão de recibo, etc.).
    SALE((short)33),       // Pagamento de mercadorias ou serviços.
    SALEVOID((short)34);   // Estorna uma transação de venda que foi previamente realizada e confirmada

    private final short value;

    PWOper(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }
}
