package br.com.paygo.interop;

import br.com.paygo.enums.PTIBeep;
import br.com.paygo.enums.PTICnf;
import br.com.paygo.enums.PWInfo;
import br.com.paygo.enums.PWOper;
import com.sun.jna.ptr.ShortByReference;

public class LibFunctions {
    static private PTILib ptiLib = new PTILib();

    /**
     * => PTI_Init:
     *
     * Esta função configura a biblioteca de integração e deve ser a primeira a ser chamada pela Automação
     * Comercial. A biblioteca de integração somente aceitará conexões do terminal de pagamento após
     * sua chamada.
     */
    public static void init(String company, String version, String capabilities, String workingDir, short tcpPort, short maxTerminals, String waitMessage, short timeout, ShortByReference ret) {
        ptiLib.init(company, version, capabilities, workingDir, tcpPort, maxTerminals, waitMessage, timeout, ret);
    }

    /**
     * => PTI_ConnectionLoop:
     *
     * Esta função permite que a Automação Comercial verifique quando um novo terminal se conectou.
     */
    public static void connectionLoop(byte[] terminalId, byte[] model, byte[] MAC, byte[] serialNumber, ShortByReference ret) {
        ptiLib.connectionLoop(terminalId, model, MAC, serialNumber, ret);
    }

    /**
     * => PTI_Display:
     * Esta função apresenta uma mensagem na tela do terminal e retorna imediatamente.
     */
    public static void displayMessage(byte[] terminalId, String message, ShortByReference ret) {
        ptiLib.displayMessage(terminalId, message, ret);
    }

    /**
     * => PTI_End:
     *
     * Esta função deve ser a última função chamada pela Automação Comercial, quando finalizada ou
     * antes de descarregar a biblioteca de integração.
     * Neste momento, a biblioteca de integração libera todos recursos alocados (portas TCP, processos,
     * memória, etc.).
     */
    public static void end() {
        ptiLib.end();
    }

    /**
     * => PTI_WaitKey:
     *
     * Esta função aguarda o pressionar de uma tecla no terminal e apenas retorna após uma tecla ser
     * pressionada ou quando o tempo de espera se esgotar.
     */
    public static void waitKeyPress(byte[] terminalId, int timeout, ShortByReference key, ShortByReference ret) {
        ptiLib.waitKeyPress(terminalId, (short) timeout, key, ret);
    }

    /**
     * => PTI_StartMenu:
     *
     * Esta função inicia a construção de um menu de opção para seleção pelo usuário.
     * Esta função retorna imediatamente.
     */
    public static void createMenu(byte[] terminalId,  ShortByReference ret) {
        ptiLib.createMenu(terminalId, ret);
    }

    /**
     * => PTI_AddMenuOption:
     *
     * Esta função adiciona uma opção ao menu que foi criado através de PTI_StartMenu.
     * Esta função retorna imediatamente.
     */
    public static void addMenuOption(byte[] terminalId, String optionMessage, ShortByReference ret) {
        ptiLib.addMenuOption(terminalId, optionMessage, ret);
    }

    /**
     * => PTI_ExecMenu:
     *
     * Esta função exibe o menu de opções que foi criado através de PTI_StartMenu e
     * PTI_AddMenuOption e identifica a seleção feita pelo usuário.
     */
    public static void displayMenu(byte[] terminalId, String promptMessage, int timeout, ShortByReference selectedOptionIndex, ShortByReference ret) {
        ptiLib.displayMenu(terminalId, promptMessage.getBytes(), (short) timeout, selectedOptionIndex, ret);
    }

    /**
     * => PTI_CheckStatus:
     *
     * Esta função permite que a Automação Comercial verifique o status (on-line ou offline) de
     * determinado terminal de pagamento e recupere informações adicionais do equipamento.
     */
    public static void checkStatus(byte[] terminalId, ShortByReference status, byte[] model, byte[] mac, byte[] serialNumber, ShortByReference ret) {
        ptiLib.checkStatus(terminalId, status, model, mac, serialNumber, ret);
    }

    /**
     * => PTI_ClearKey:
     *
     * Esta função limpa o buffer de teclas pressionadas, para que a próxima chamada da função não
     * considere qualquer tecla previamente pressionada.
     * Esta função retorna imediatamente.
     */
    public static void clearKeys(byte[] terminalId, ShortByReference ret) {
        ptiLib.clearKeys(terminalId, ret);
    }

    /**
     * => PTI_Disconnect:
     *
     * Esta função permite que a Automação Comercial desconecte um terminal de pagamento e o coloque
     * em modo offline, seja imediatamente ou após algum tempo funcionando sem alimentação externa.
     * Para terminais móveis, permanecer on-line aumenta consideravelmente o consumo da bateria.
     */
    public static void disconnect(byte[] terminalId, int limitTime, ShortByReference ret) {
        ptiLib.disconnect(terminalId, (short) limitTime, ret);
    }

    /**
     * => PTI_GetData:
     *
     * Esta função realiza a captura de um único dado em um terminal previamente conectado.
     * Esta função é blocante e somente retorna após a captura de dado ser bem-sucedida ou falha.
     */
    public static void getData(byte[] terminalId, String promptMessage, String format, int minLength, int maxLength,
                               boolean startFromLeft, boolean alphanumericCharacters, boolean hasMask,
                               int timeout, byte[] data, int startLine, ShortByReference ret) {
        ptiLib.getData(terminalId, promptMessage.getBytes(), format.getBytes(), (short)minLength, (short)maxLength, startFromLeft ? 1 : 0,
                alphanumericCharacters ? 1 : 0, hasMask ? 1 : 0, (short)timeout, data, (short)startLine, ret);
    }

    /**
     * => PTI_EFT_Start:
     *
     * A Automação Comercial deve chamar esta função para iniciar qualquer nova transação.
     * Esta função retorna imediatamente.
     */
    public static void startTransaction(byte[] terminalId, PWOper operation, ShortByReference ref) {
        ptiLib.startTransaction(terminalId, operation.getValue(), ref);
    }

    /**
     * => PTI_EFT_AddParam:
     *
     * A Automação Comercial deve chamar esta função iterativamente após PTI_EFT_Start para definir
     * todos os parâmetros disponíveis para a transação.
     */
    public static void addParam(byte[] terminalId, PWInfo param, String paramValue, ShortByReference ret) {
        ptiLib.addParam(terminalId, (short)param.getValue(), paramValue.getBytes(), ret);
    }

    public static void addParam(byte[] terminalId, PWInfo param, byte[] paramValue, ShortByReference ret) {
        ptiLib.addParam(terminalId, (short)param.getValue(), paramValue, ret);
    }

    /**
     * => PTI_EFT_Exec:
     *
     * Esta função efetua de fato a transação, utilizando os parâmetros que foram previamente definidos
     * através de PTI_EFT_AddParam.
     */
    public static void executeTransaction(byte[] terminalId, ShortByReference ret) {
        ptiLib.executeTransaction(terminalId, ret);
    }

    /**
     * => PTI_Print:
     *
     * Esta função imprime uma ou mais linhas de texto na impressora do terminal e retorna
     * imediatamente.
     */
    public static void printContent(byte[] terminalId, byte[] message, ShortByReference ret) {
        ptiLib.printContent(terminalId, message, ret);
    }

    /**
     * => PTI_PrnSymbolCode:
     *
     * Esta função imprime um código de barras ou QR code na impressora do terminal.
     */
    public static void printCodeBar(byte[] terminalId, String code, int symbology, ShortByReference ret) {
        ptiLib.printCodeBar(terminalId, code.getBytes(), (short)symbology, ret);
    }

    /**
     * => PTI_PrnFeed:
     *
     * Esta função avança algumas linhas do papel da impressora, para permitir que o usuário destaque o
     * recibo.
     */
    public static void printEmptySpace(byte[] terminalId, ShortByReference ret) {
        ptiLib.printEmptySpace(terminalId, ret);
    }

    /**
     * => PTI_EFT_PrintReceipt:
     *
     * Esta função faz com que o terminal imprima o comprovante da última transação realizada.
     */
    public static void printReceipt(byte[] terminalId, int receiptCopies, ShortByReference ret) {
        ptiLib.printReceipt(terminalId, (short)receiptCopies, ret);
    }

    /**
     * => PTI_Beep:
     *
     * Esta função emite um aviso sonoro no terminal.
     */
    public static void beep(byte[] terminalId, PTIBeep beep, ShortByReference ret) {
        ptiLib.beep(terminalId, (short)beep.ordinal(), ret);
    }

    /**
     * => PTI_EFT_Confirm:
     *
     * Qualquer transação financeira bem-sucedida deve ser
     * confirmada pela Automação Comercial através desta função para assegurar a integridade da
     * transação entre todas as partes (Automação Comercial e registro fiscais, terminal, adquirente,
     * emissor e portador do cartão).
     */
    public static void confirmTransaction(byte[] terminalId, PTICnf confirmationStatus, ShortByReference ret) {
        ptiLib.confirmTransaction(terminalId, (short)confirmationStatus.getValue(), ret);
    }

    /**
     * => PTI_EFT_GetInfo:
     *
     * A Automação Comercial deve chamar esta função iterativamente para recuperar os dados relativos à
     * transação que foi realizada (com ou sem sucesso) pelo terminal.
     */
    public static void getInfo(byte[] terminalId, PWInfo info, byte[] value, ShortByReference ret) {
        ptiLib.getInfo(terminalId, (short)info.getValue(), (short)value.length, value, ret);
    }
}
