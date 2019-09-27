package br.com.paygo;

import br.com.paygo.enums.PTIKey;
import br.com.paygo.enums.PTIRet;
import br.com.paygo.interop.LibFunctions;
import com.sun.jna.ptr.ShortByReference;

public class POS implements Runnable {

    private final byte[] terminalId;
    private final byte[] model;
    private final byte[] mac;
    private final byte[] serialNumber;

    public POS(byte[] terminalId, byte[] model, byte[] mac, byte[] serialNumber) {
        this.terminalId = terminalId;
        this.model = model;
        this.mac = mac;
        this.serialNumber = serialNumber;
    }

    private void waitAction() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference key = new ShortByReference(PTIKey.KEY_FUNC2.getValue());
        ShortByReference status = new ShortByReference((short)-1);

        LibFunctions.displayMessage(terminalId, "TERMINAL CONECTADO!", returnedCode);

        System.out.println("=> DisplayMessage: " + returnedCode.getValue());

//        LibFunctions.checkStatus(terminalId, status, model, MAC, serialNumber, returnedCode);
//
//        System.out.println("=> CheckStatus: " + returnedCode.getValue());

        System.out.println("Pressione OK...");
        LibFunctions.waitKeyPress(terminalId, 10, key, returnedCode);

        System.out.println("=> WaitKeyPress: " + returnedCode.getValue());

        displayMainMenu();

        LibFunctions.disconnect(terminalId, returnedCode);
    }

    private void displayMainMenu() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference key = new ShortByReference(PTIKey.KEY_FUNC2.getValue());
        int actionCode = -1;
        ShortByReference optionSelected = new ShortByReference((short)-1);

        do {
            LibFunctions.createMenu(terminalId, returnedCode);
            System.out.println("=> createMenu: " + returnedCode.getValue());
            LibFunctions.addMenuOption(terminalId, "Operacoes", returnedCode);
            System.out.println("=> addMenuOption: " + returnedCode.getValue());
            LibFunctions.addMenuOption(terminalId, "Captura de Dados", returnedCode);
            System.out.println("=> addMenuOption: " + returnedCode.getValue());
            LibFunctions.addMenuOption(terminalId, "Impressao", returnedCode);
            System.out.println("=> addMenuOption: " + returnedCode.getValue());
            LibFunctions.addMenuOption(terminalId, "Desconectar", returnedCode);
            System.out.println("=> addMenuOption: " + returnedCode.getValue());

            LibFunctions.displayMenu(terminalId, "Selecione uma opcao:", 30, optionSelected, returnedCode);
            System.out.println("=> displayMenu: " + returnedCode.getValue());

            if (optionSelected.getValue() == 190) {
                LibFunctions.displayMessage(terminalId, "NENHUMA OPCAO\rSELECIONADA", returnedCode);
            } else {
                switch (optionSelected.getValue()) {
                    case 0:
                        System.out.println("MENU OPERACOES");
                        menuOperations();
                        break;
                    case 1:
                        System.out.println("MENU CAPTURA DE DADOS");
                        //actionCode = menuDataRetrieve();
                        break;
                    case 2:
                        System.out.println("MENU IMPRESSAO");
                        //actionCode = menuPrint();
                        break;
                }
            }
        } while (optionSelected.getValue() != 3);
        /*
        do {
            LibFunctions.clearKeys(terminalId, returnedCode);
            System.out.println("=> clearKeys: " + returnedCode.getValue());

            LibFunctions.displayMenu(terminalId, "Selecione uma opcao:", 30, optionSelected, returnedCode);
            System.out.println("=> displayMenu: " + returnedCode.getValue());


            // TODO:
            // Tratar clique no botão X

            if (returnedCode.getValue() == PTIRet.TIMEOUT.getValue()) {
                LibFunctions.displayMessage(terminalId, "Tempo de espera esgotado", returnedCode);
                System.out.println("Pressione OK...");
                LibFunctions.waitKeyPress(terminalId, 5, key, returnedCode);

                optionSelected.setValue((short)3);
            } else {
                switch (optionSelected.getValue()) {
                    case 0:
                        System.out.println("MENU OPERACOES");
                        actionCode = menuOperations();
                        break;
                    case 1:
                        System.out.println("MENU CAPTURA DE DADOS");
                        //actionCode = menuDataRetrieve();
                        break;
                    case 2:
                        System.out.println("MENU IMPRESSAO");
                        //actionCode = menuPrint();
                        break;
                }
            }
        } while (actionCode != 1 && optionSelected.getValue() != 3);

        LibFunctions.disconnect(terminalId, returnedCode);
        */
    }

    private void menuOperations() {
        ShortByReference returnedCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());
        ShortByReference key = new ShortByReference(PTIKey.KEY_FUNC2.getValue());
        ShortByReference optionSelected = new ShortByReference((short)-1);

        LibFunctions.clearKeys(terminalId, returnedCode);
        System.out.println("=> clearKeys: " + returnedCode.getValue());
        LibFunctions.createMenu(terminalId, returnedCode);
        System.out.println("=> createMenu: " + returnedCode.getValue());
        LibFunctions.addMenuOption(terminalId, "Venda", returnedCode);
        System.out.println("=> addMenuOption: " + returnedCode.getValue());
        LibFunctions.addMenuOption(terminalId, "Cancelamento", returnedCode);
        System.out.println("=> addMenuOption: " + returnedCode.getValue());
        LibFunctions.addMenuOption(terminalId, "Administrativo", returnedCode);
        System.out.println("=> addMenuOption: " + returnedCode.getValue());
        LibFunctions.addMenuOption(terminalId, "Retornar", returnedCode);
        System.out.println("=> addMenuOption: " + returnedCode.getValue());
        LibFunctions.displayMenu(terminalId, "Selecione uma opcao:", 30, optionSelected, returnedCode);
        System.out.println("=> displayMenu: " + returnedCode.getValue());

        switch (optionSelected.getValue()) {
            case 0:
                System.out.println("VENDA");
                // sale();
                break;
            case 1:
                System.out.println("SALEVOID");
                // saleVoid();
                break;
            case 2:
                System.out.println("ADMIN");
                // admin();
                break;
        }

        /*
        do {
            LibFunctions.displayMenu(terminalId, "Selecione uma opcao:", 30, optionSelected, returnedCode);
            System.out.println("=> displayMenu: " + returnedCode.getValue());

            if (returnedCode.getValue() == PTIRet.TIMEOUT.getValue()) {
                LibFunctions.displayMessage(terminalId, "Tempo de espera esgotado", returnedCode);
                System.out.println("Pressione OK...");
                LibFunctions.waitKeyPress(terminalId, 5, key, returnedCode);

                return 1;
            } else {

            }
        } while (optionSelected.getValue() != 3);
        */
    }

    @Override
    public void run() {
        System.out.println("RUNNING " + terminalId);
        waitAction();
    }
}
