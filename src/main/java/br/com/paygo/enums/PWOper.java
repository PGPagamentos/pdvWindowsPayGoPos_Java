package br.com.paygo.enums;

/**
 * Tipos de operações disponibilizadas
 */
public enum PWOper {
    ADMIN(32),      // Qualquer transação que não seja um pagamento(estorno, pré-autorização, consulta, relatório, reimpressão de recibo, etc.).
    SALE(33),       // Pagamento de mercadorias ou serviços.
    SALEVOID(34);   // Estorna uma transação de venda que foi previamente realizada e confirmada

    private final int value;

    PWOper(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
