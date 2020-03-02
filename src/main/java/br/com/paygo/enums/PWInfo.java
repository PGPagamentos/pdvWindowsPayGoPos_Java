package br.com.paygo.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Tipos de dados que podem ser informados pela Automação
 */
public enum PWInfo {
    UNKNOWN(0),
    OPERATION(2),         //Transação que foi realizada: “00” – Sem Definição “01” – Venda (pagamento) “02” – Administrativa (geral) “04” – Estorno de Venda
    MERCHANTCNPJCPF(28),  //CNPJ (ou CPF) do Estabelecimento / Ponto de captura
    TOTAMNT(37),          //Valor da transação, em centavos. Este parâmetro é mandatório para transações de venda (PTITRN_SALE) e de estorno (PTITRN_SALEVOID)
    CURRENCY(38),         //Código da moeda, conforme o padrão internacional ISO4217 (986 para Real, 840 para Dólar americano). Este parâmetro é requerido sempre que TOTAMNT for informado
    FISCALREF(40),        //Número da fatura (final nulo). Este parâmetro é opcional.
    CARDTYPE(41),         //Modalidade da transação do cartão: 1: crédito), 2: débito), 4: voucher), 8: private label), 16: frota), 128: outros.
    PRODUCTNAME(42),      //Nome/tipo do produto utilizado, na nomenclatura do Provedor.
    DATETIME(49),         //Horário do servidor PGWEB, format “YYMMDDhhmmss”.
    REQNUM(50),           //Identificador único da transação (gerado pelo terminal).
    AUTHSYST(53),         //Código da Rede Adquirente, conforme descrito no item 5.5. Caso este campo seja preenchido, a transação será realizada diretamente na rede adquirente especificada
    VIRTMERCH(54),        //Identificador da afiliação utilizada para o sistema de gerenciamento do terminal.
    AUTMERCHID(56),       //Identificador do estabelecimento para a adquirente.
    FINTYPE(59),          //Modalidade de financiamento da transação: 1: à vista), 2: parcelado pelo Emissor), 4: parcelado pelo Estabelecimento), 8: pré-datado), 16: crédito emissor), 32: Prédatado parcelado.
    INSTALLMENTS(60),     //Quantidade de parcelas, para transações parceladas.
    INSTALLMDATE(61),     //Data de vencimento do prédatado, ou da primeira parcela. Formato “DDMMAA”.
    RESULTMSG(66),        //Mensagem de texto que descreve o resultado da transação (sucesso ou falha).
    AUTLOCREF(68),        //Identificador único da transação (gerado pelo sistema de gerenciamento do terminal).
    AUTEXTREF(69),        //Identificador único da transação (gerado pela adquirente/processadora)
    AUTHCODE(70),         //Código de autorização (gerado pelo emissor).
    AUTRESPCODE(71),      //Caso a transação chegue ao sistema autorizador, esse é o código de resposta do mesmo  (bit39 da mensagem ISO8583).
    DISCOUNTAMT(73),      //Valor do desconto concedido pelo Provedor, considerando CURREXP, já deduzido em TOTAMNT.
    CASHBACKAMT(74),      //Valor do saque/troco, considerando CURREXP, já incluído em TOTAMNT.
    CARDNAME(75),         //Nome do cartão ou emissor
    BOARDINGTAX(77),      //Valor da taxa de embarque, considerando CURREXP, já incluído em TOTAMNT.
    TIPAMOUNT(78),        //Valor da taxa de serviço (gorjeta), considerando CURREXP, já incluído em TOTAMNT.
    RCPTMERCH(83),        //Comprovante – via do estabelecimento.
    RCPTCHOLDER(84),      //Comprovante – via do portador.
    RCPTCHSHORT(85),      //Comprovante – via reduzida
    TRNORIGDATE(87),      //Data da transação original. Este campo é utilizado para transações de cancelamento. Formato DDMMAA.
    TRNORIGNSU(88),       //Número de referência da transação original (atribuído pela adquirente/processadora). Este parâmetro é mandatório para transações de estorno (PTITRN_SALEVOID).
    TRNORIGAUTH(98),      //Código de autorização da transação original. Este campo é utilizado para transações de cancelamento.
    LANGUAGE(108),        //Idioma a ser utilizado para a interface com o cliente: 0: Português 1: Inglês 2: Espanhol
    TRNORIGTIME(115),     //Horário da transação original. Este campo é utilizado para transações de cancelamento. Formato HHMMSS.
    PWPTI_RESULT(129),    //Caso a execução da função retorne PTIRET_EFTERR, este campo informa o detalhamento do erro.
    CARDENTMODE(192),     //Modo de entrada do cartão:  1: número do cartão digitado 2: tarja magnética 4: chip com contato EMV  16: fallback para tarja magnética  32: chip sem contato simulando tarja magnética 64: chip sem contato EMV 128: indica que a transação atual é oriunda de um fallback (flag enviado do servidor para o ponto de captura). 256: fallback de tarja para digitado
    CARDPARCPAN(200),     //Número do cartão mascarado
    CHOLDVERIF(207),      //Verificação do portador do cartão, soma de:  1: assinatura 2: verificação offline da senha 4: senha offline bloqueada durante a transação  8: verificação on-line da senha.
    MERCHADDDATA1(240),   //Número de referência da transação atribuído pela Automação Comercial. Caso fornecido, este número será incluído no histórico de dados da transação e encaminhado à adquirente/processadora, se suportado. Este parâmetro é opcional.
    MERCHADDDATA2(241),   //Dados adicionais específicos do negócio. Caso fornecido, será incluso no histórico de dados da transação, por exemplo para referências cruzadas. Este parâmetro é opcional.
    MERCHADDDATA3(242),   //Dados adicionais específicos do negócio. Caso fornecido, será incluso no histórico de dados da transação, por exemplo para referências cruzadas. Este parâmetro é opcional.
    MERCHADDDATA4(243),   //Dados adicionais específicos do negócio. Caso fornecido, será incluso no histórico de dados da transação, por exemplo para referências cruzadas. Este parâmetro é opcional.
    PNDAUTHSYST(32517),   //Nome do provedor para o qual existe uma transação pendente.
    PNDVIRTMERCH(32518),  //Identificador do Estabelecimento para o qual existe uma transação pendente.
    PNDAUTLOCREF(32520),  //Referência para a infraestrutura Erro! Nome de propriedade do documento desconhecido. da transação que está pendente.
    PNDAUTEXTREF(32521),  //Referência para o Provedor da transação que está pendente.
    DUEAMNT(48902),       //Valor devido pelo usuário, considerando CURREXP, já deduzido em TOTAMNT.
    READJUSTEDAMNT(48905),//Valor total da transação reajustado, este campo será utilizado caso o autorizador, por alguma regra de negócio específica dele, resolva alterar o valor total que foi solicitado para a transação.
    CARDNAMESTD(196),   // Descrição do produto bandeira padrão relacionado ao BIN.
    CHOLDERNAME(7992);  //Nome do portador do cartão utilizado, o tamanho segue o mesmo padrão da tag 5F20 EMV.

    private int value;

    PWInfo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private void setValue(int value) {
        this.value = value;
    }

    public static PWInfo valueOf(short value) {
        Optional<PWInfo> info = Arrays.stream(values()).filter(pwInfo -> pwInfo.value == value).findAny();

        if (info.isPresent()) {
            return info.get();
        } else {
            UNKNOWN.setValue(value);
            return UNKNOWN;
        }
    }
}
