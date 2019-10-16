package br.com.paygo;

import br.com.paygo.enums.*;
import br.com.paygo.helper.Printer;
import br.com.paygo.interop.LibFunctions;
import br.com.paygo.ui.UserInterface;
import com.sun.jna.ptr.ShortByReference;

public class POS implements Runnable {

    private static final ShortByReference defaultKey = new ShortByReference(PTIKey.KEY_FUNC2.getValue());

    private final UserInterface userInterface;

    private final byte[] terminalId;
    private final byte[] model;
    private final byte[] mac;
    private final byte[] serialNumber;

    public POS(UserInterface userInterface, byte[] terminalId, byte[] model, byte[] mac, byte[] serialNumber) {
        this.userInterface = userInterface;
        this.terminalId = terminalId;
        this.model = model;
        this.mac = mac;
        this.serialNumber = serialNumber;
    }

    /**
     * Método que fica aguardando a conexão dos terminais
     */
    private void waitAction() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference status = new ShortByReference((short)-1);

        LibFunctions.displayMessage(terminalId, "TERMINAL CONECTADO!\rID: " + Printer.format(terminalId), returnedCode);
        userInterface.logInfo("=> DisplayMessage: " + returnedCode.getValue());

        userInterface.logInfo("Terminal Conectado: " + Printer.format(terminalId));

        userInterface.logInfo("Pressione OK...");
        LibFunctions.waitKeyPress(terminalId, 10, defaultKey, returnedCode);
        userInterface.logInfo("=> WaitKeyPress: " + returnedCode.getValue());

        LibFunctions.checkStatus(terminalId, status, model, mac, serialNumber, returnedCode);
        userInterface.logInfo("=> CheckStatus: " + returnedCode.getValue());

        LibFunctions.displayMessage(terminalId, "SERIAL: " + Printer.format(serialNumber) + "\rMAC: " + Printer.format(mac) + "\rMODELO: " + Printer.format(model) + "\rSTATUS: " + status, returnedCode);
        userInterface.logInfo("=> DisplayMessage: " + returnedCode.getValue());

        System.out.println("Pressione OK...");
        LibFunctions.waitKeyPress(terminalId, 10, defaultKey, returnedCode);
        userInterface.logInfo("=> WaitKeyPress: " + returnedCode.getValue());

        displayMainMenu();

        LibFunctions.displayMessage(terminalId, "DESCONECTANDO POS", returnedCode);

        userInterface.logInfo("...\nDESCONECTANDO (TERMINAL " + terminalId + ")\n...");

        LibFunctions.disconnect(terminalId, 5, returnedCode);
        userInterface.logInfo("=> PTI_Disconnect: " + returnedCode.getValue());

        if (returnedCode.getValue() == PTIRet.OK.getValue()) {
            PTI.connectedPOS.remove(new String(this.terminalId));
        }

        System.out.println(PTI.connectedPOS.size());
    }

    /**
     * Método responsável por definir o menu principal que será exibido na tela do terminal
     */
    private void displayMainMenu() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference optionSelected;
        int actionCode = -1;

        do {
            optionSelected = new ShortByReference((short)-1);

            LibFunctions.createMenu(terminalId, returnedCode);

            LibFunctions.addMenuOption(terminalId, "Operacoes", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Captura de Dados", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Impressao", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Desconectar", returnedCode);

            LibFunctions.clearKeys(terminalId, returnedCode);

            LibFunctions.displayMenu(terminalId, "Selecione uma opcao:", 30, optionSelected, returnedCode);

            if (returnedCode.getValue() == PTIRet.TIMEOUT.getValue()) {
                timeoutAction();
                optionSelected = new ShortByReference((short)3);
            }

            switch (optionSelected.getValue()) {
                case 0:
                    System.out.println("MENU OPERACOES");
                    actionCode = menuOperations();
                    break;
                case 1:
                    System.out.println("MENU CAPTURA DE DADOS");
                    actionCode = menuDataRetrieve();
                    break;
                case 2:
                    System.out.println("MENU IMPRESSAO");
                    actionCode = menuPrint();
                    break;
            }
        } while (optionSelected.getValue() != 3 && actionCode != 1);
    }

    /**
     * Neste menu são exibidas as principais operações disponibilizadas para o terminal de atendimento.
     */
    private int menuOperations() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference optionSelected;

        LibFunctions.clearKeys(terminalId, returnedCode);

        do {
            optionSelected = new ShortByReference((short)-1);

            LibFunctions.createMenu(terminalId, returnedCode);

            LibFunctions.addMenuOption(terminalId, "Venda", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Cancelamento", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Administrativo", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Retornar", returnedCode);

            LibFunctions.clearKeys(terminalId, returnedCode);

            LibFunctions.displayMenu(terminalId, "Selecione uma opcao:", 30, optionSelected, returnedCode);

            if (returnedCode.getValue() == PTIRet.TIMEOUT.getValue()) {
                return timeoutAction();
            } else {
                switch (optionSelected.getValue()) {
                    case 0:
                        System.out.println("=== VENDA ===");
                        sale();
                        return 1;
                    case 1:
                        System.out.println("=== SALEVOID ===");
                        saleVoid();
                        return 1;
                    case 2:
                        System.out.println("=== ADMIN ===");
                        admin();
                        break;
                }
            }
        } while (optionSelected.getValue() != 3);

        return 0;
    }

    private void sale() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference optionSelected = new ShortByReference((short)-1);
        byte[] saleValue = new byte[1000];

        System.out.println("DIGITE VALOR PAGAMENTO");

        LibFunctions.getData(terminalId, "DIGITE VALOR PAGAMENTO", "@@@.@@@,@@",
                3, 8, false, false, false,
                30, saleValue, 3, returnedCode);

        userInterface.logInfo("=> PTI_GetData: " + returnedCode.getValue());

        LibFunctions.startTransaction(terminalId, PWOper.SALE, returnedCode);

        LibFunctions.addParam(terminalId, PWInfo.TOTAMNT, saleValue, returnedCode);
        LibFunctions.addParam(terminalId, PWInfo.CURRENCY, "986", returnedCode);

        userInterface.logInfo("VALOR INFORMADO = " + Printer.format(saleValue) + " (TERMINAL = " + Printer.format(terminalId) + ")");

        LibFunctions.executeTransaction(terminalId, returnedCode);

        userInterface.logInfo("=> PTI_EFT_Exec: " + returnedCode.getValue());

        if (returnedCode.getValue() == PTIRet.OK.getValue()) {
            printResultParams();

            LibFunctions.printCodeBar(terminalId, "0123456789", 2, returnedCode); // código de barras
            LibFunctions.printCodeBar(terminalId, "http://www.ntk.com.br", 4, returnedCode); // qrcode
            LibFunctions.printEmptySpace(terminalId, returnedCode);
            LibFunctions.printReceipt(terminalId, 3, returnedCode);

            LibFunctions.beep(terminalId, PTIBeep.OK, returnedCode);
        } else {
            byte[] value = new byte[1000];

            LibFunctions.beep(terminalId, PTIBeep.OK, returnedCode);
            LibFunctions.getInfo(terminalId, PWInfo.RESULTMSG, value, returnedCode);

            userInterface.logInfo("RESULTMSG = " + Printer.format(value));
        }

        confirmation();
    }

    private void saleVoid() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference optionSelected = new ShortByReference((short)-1);

        LibFunctions.startTransaction(terminalId, PWOper.SALEVOID, returnedCode);

        LibFunctions.executeTransaction(terminalId, returnedCode);

        if (returnedCode.getValue() == PTIRet.OK.getValue()) {
            printResultParams();

            LibFunctions.printReceipt(terminalId, 3, returnedCode);

            LibFunctions.beep(terminalId, PTIBeep.OK, returnedCode);

            LibFunctions.clearKeys(terminalId, returnedCode);

            LibFunctions.createMenu(terminalId, returnedCode);

            LibFunctions.addMenuOption(terminalId, "SIM", returnedCode);
            LibFunctions.addMenuOption(terminalId, "NAO", returnedCode);

            LibFunctions.displayMenu(terminalId, "CONFIRMA TRANSACAO?:", 30, optionSelected, returnedCode);

            if (optionSelected.getValue() == 0) {
                LibFunctions.confirmTransaction(terminalId, PTICnf.SUCCESS, returnedCode);
            } else {
                LibFunctions.confirmTransaction(terminalId, PTICnf.OTHERERR, returnedCode);
            }
        } else {
            userInterface.logInfo("Operação foi cancelada ou falhou");
            userInterface.logInfo("CÓDIGO RETORNADO: " + returnedCode);

            LibFunctions.beep(terminalId, PTIBeep.ERROR, returnedCode);
        }
    }

    private void admin() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());

        LibFunctions.startTransaction(terminalId, PWOper.ADMIN, returnedCode);

        LibFunctions.executeTransaction(terminalId, returnedCode);

        userInterface.logInfo("=> PTI_EFT_Exec: " + returnedCode.getValue());

        // Não retornou OK, vai para a confirmação. Pode existir alguma transação pendente ou com erro.
        if (returnedCode.getValue() != PTIRet.OK.getValue()) {
            userInterface.logInfo("RETORNO DA OPERAÇÃO: " + returnedCode);
            confirmation();
        }
    }

    /**
     * Neste menu são exibidas as opções disponíveis para captura de dados do usuário (mascarados ou não).
     */
    private int menuDataRetrieve() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference optionSelected;
        byte[] value;

        do {
            value = new byte[1000];
            optionSelected = new ShortByReference((short)-1);

            LibFunctions.createMenu(terminalId, returnedCode);

            LibFunctions.addMenuOption(terminalId, "CPF Mascarado", returnedCode);
            LibFunctions.addMenuOption(terminalId, "CPF nao mascarado", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Retornar", returnedCode);

            LibFunctions.displayMenu(terminalId, "Selecione uma opcao:", 30, optionSelected, returnedCode);

            if (returnedCode.getValue() == PTIRet.TIMEOUT.getValue()) {
                return timeoutAction();
            } else if (optionSelected.getValue() == 0) {
                LibFunctions.getData(terminalId, "CPF C/ MASCARA", "@@@.@@@.@@@-@@",
                        11, 11, true, false, true,
                        30, value, 3, returnedCode);

                userInterface.logInfo("CPF com máscara capturado: " + Printer.format(value)  + " (TERMINAL = " + Printer.format(terminalId) + ")");

                LibFunctions.waitKeyPress(terminalId, 5, defaultKey, returnedCode);
            } else if (optionSelected.getValue() == 1) {
                LibFunctions.getData(terminalId, "CPF S/ MASCARA", "",
                        11, 11, true, false, false,
                        30, value, 3, returnedCode);

                userInterface.logInfo("CPF sem máscara capturado: " + Printer.format(value)  + " (TERMINAL = " + Printer.format(terminalId) + ")");

                LibFunctions.waitKeyPress(terminalId, 5, defaultKey, returnedCode);
            }
        } while (optionSelected.getValue() != 2);

        return 0;
    }

    /**
     * Neste menu são exibidas as opções de impressão disponíveis.
     */
    private int menuPrint() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference optionSelected;

        LibFunctions.clearKeys(terminalId, returnedCode);

        do {
            LibFunctions.createMenu(terminalId, returnedCode);

            LibFunctions.addMenuOption(terminalId, "PrintReceipt", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Display", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Print", returnedCode);
            LibFunctions.addMenuOption(terminalId, "PrnSymbolcode", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Retornar", returnedCode);

            optionSelected = new ShortByReference((short)-1);

            LibFunctions.displayMenu(terminalId, "Selecione uma opcao:", 30, optionSelected, returnedCode);

            if (returnedCode.getValue() == PTIRet.TIMEOUT.getValue()) {
                return timeoutAction();
            } else {
                switch (optionSelected.getValue()) {
                    case 0: // Impressão de recibo da venda
                        LibFunctions.printReceipt(terminalId, 3, returnedCode);
                        userInterface.logInfo("=> PTI_EFT_PrintReceipt: " + returnedCode.getValue());

                        if (returnedCode.getValue() == PTIRet.NODATA.getValue()) {
                            LibFunctions.displayMessage(terminalId, "SEM RECECIBO\rPARA IMPRIMIR!", returnedCode);
                            userInterface.logInfo("Não existe recibo a ser impresso");
                        } else if (returnedCode.getValue() == PTIRet.NOPAPER.getValue()) {
                            LibFunctions.displayMessage(terminalId, "IMPRESSORA SEM\rPAPEL!", returnedCode);
                            userInterface.logInfo("Impressora sem papel");
                        } else if (returnedCode.getValue() == PTIRet.INTERNALERR.getValue()) {
                                LibFunctions.displayMessage(terminalId, "ERRO INTERNO!", returnedCode);
                            userInterface.logInfo("Erro interno da biblioteca de integração");
                        }

                        return 0;
                    case 1: // Exibe mensagem na tela do equipamento
                        LibFunctions.displayMessage(terminalId, "Exibindo mensagem...", returnedCode);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ignore) {}
                        return 0;
                    case 2: // Imprime um texto digitado pelo usuário
                        byte[] printValue = new byte[1000];

                        LibFunctions.getData(terminalId, "TEXTO PARA IMPRIMIR\r", "@@@@@@",
                                1, 6, false, true, false,
                                30, printValue, 2, returnedCode);

                        userInterface.logInfo("=> PTI_GetData: " + returnedCode.getValue());
                        userInterface.logInfo("TEXTO INFORMADO = " + Printer.format(printValue) + " (TERMINAL = " + Printer.format(terminalId) + ")");

                        LibFunctions.printContent(terminalId, "TEXTO INFORMADO: ".getBytes(), returnedCode);
                        userInterface.logInfo("=> PTI_Print: " + returnedCode.getValue());

                        LibFunctions.printContent(terminalId, printValue, returnedCode);
                        userInterface.logInfo("=> PTI_Print: " + returnedCode.getValue());

                        LibFunctions.printEmptySpace(terminalId, returnedCode);
                        userInterface.logInfo("=> PTI_EFT_PrintReceipt: " + returnedCode.getValue());

                        return 0;
                    case 3: // Opções de impressão de código de barras e QR
                        symbolsMenu();
                        return 0;
                }
            }
        } while (optionSelected.getValue() != 4);

        return 0;
    }

    private int symbolsMenu() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference optionSelected;

        LibFunctions.clearKeys(terminalId, returnedCode);

        do {
            LibFunctions.createMenu(terminalId, returnedCode);

            LibFunctions.addMenuOption(terminalId, "QR Code", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Cod Barras", returnedCode);
            LibFunctions.addMenuOption(terminalId, "Retornar", returnedCode);

            optionSelected = new ShortByReference((short) -1);

            LibFunctions.displayMenu(terminalId, "Selecione uma opcao:", 30, optionSelected, returnedCode);

            if (returnedCode.getValue() == PTIRet.TIMEOUT.getValue()) {
                return timeoutAction();
            } else {
                if (optionSelected.getValue() == 0) {
                    System.out.println("Imprimindo QR Code");

                    LibFunctions.printCodeBar(terminalId, "http://www.ntk.com.br", 4, returnedCode);

                    LibFunctions.printEmptySpace(terminalId, returnedCode);

                    return 0;
                } else if (optionSelected.getValue() == 1) {
                    System.out.println("Imprimindo Cod. Barras");

                    LibFunctions.printCodeBar(terminalId, "0123456789", 2, returnedCode);

                    LibFunctions.printEmptySpace(terminalId, returnedCode);

                    return 0;
                }
            }
        } while (optionSelected.getValue() != 2);

        return 0;
    }

    private void printResultParams() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        byte[] value;

        userInterface.logInfo("===== PARÂMETROS DA APLICAÇÃO =====");

        for (PWInfo info : PWInfo.values()) {
             value = new byte[1000];

            if (info != PWInfo.UNKNOWN) {
                LibFunctions.getInfo(terminalId, info, value, returnedCode);
                String result = info + " = " + Printer.format(value);
                LibFunctions.printContent(terminalId, result.getBytes(), returnedCode);
                userInterface.logInfo("=> PTI_Print:" + returnedCode.getValue());
                userInterface.logInfo(info + " = " + Printer.format(value));
            }
        }

        userInterface.logInfo("===================================");
    }

    /**
     * Método responsável por executar o fluxo de confirmação de uma transação
     */
    private void confirmation() {
        userInterface.logInfo("=== CONFIRMAÇÃO DE TRANSAÇÃO ===");
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        LibFunctions.clearKeys(terminalId, returnedCode);
        ShortByReference optionSelected = new ShortByReference((short) -1);

        LibFunctions.createMenu(terminalId, returnedCode);

        LibFunctions.addMenuOption(terminalId, "SIM", returnedCode);
        LibFunctions.addMenuOption(terminalId, "NAO", returnedCode);

        LibFunctions.displayMenu(terminalId, "CONFIRMA TRANSACAO?", 30, optionSelected, returnedCode);

        if (optionSelected.getValue() == 0) {
            LibFunctions.confirmTransaction(terminalId, PTICnf.SUCCESS, returnedCode);
            userInterface.logInfo("CONFIRMAÇÃO - SUCCESS: " + returnedCode);
        } else {
            LibFunctions.confirmTransaction(terminalId, PTICnf.OTHERERR, returnedCode);
            userInterface.logInfo("CONFIRMAÇÃO - OTHERERR: " + returnedCode);
        }
    }

    private int timeoutAction() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());

        LibFunctions.displayMessage(terminalId, "TEMPO DE ESPERA\rESGOTADO!", returnedCode);

        LibFunctions.waitKeyPress(terminalId, 10, defaultKey, returnedCode);

        return 1;
    }

    String getTerminalId() {
        return new String(this.terminalId);
    }

    @Override
    public void run() {
        userInterface.logInfo("RUNNING (TERMINAL " + Printer.format(terminalId) + ")");
        waitAction();
    }
}
