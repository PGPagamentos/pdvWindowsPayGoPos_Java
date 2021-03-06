package br.com.paygo;

import br.com.paygo.enums.ApplicationProperties;
import br.com.paygo.enums.PTIRet;
import br.com.paygo.helper.Printer;
import br.com.paygo.interop.LibFunctions;
import br.com.paygo.ui.UserInterface;
import com.sun.jna.ptr.ShortByReference;

import java.util.ArrayList;

/**
 * Classe responsável por executar a thread que ficará aguardando as conexões de POS.
 */
public class PTI implements Runnable {

    private static final String COMPANY = "NTK SOLUTIONS";
    private static final String VERSION = ApplicationProperties.INSTANCE.getAppVersion();
    private static final String CAPABILITIES = "0";
    private static final String WORKING_DIR = ".";
    private static final short PORT = 10000;
    private static final short MAX_TERMINALS = 50;
    private static final String WAIT_MESSAGE = "APLICACAO EXEMPLO";
    private static final short TIMEOUT = 30;

    private final UserInterface userInterface;

    private volatile boolean keepAlive;
    private byte[] terminalId;
    private byte[] model;
    private byte[] mac;
    private byte[] serialNumber;
    static ArrayList connectedPOS = new ArrayList<String>();

    public PTI(UserInterface userInterface) {
        this.userInterface = userInterface;
        terminalId = new byte[20];
        model = new byte[20];
        mac = new byte[20];
        serialNumber = new byte[25];
    }

    private void init() {
        try {
            keepAlive = true;
            ShortByReference returnedCode = new ShortByReference(PTIRet.OK.getValue());

            LibFunctions.init(COMPANY, VERSION, CAPABILITIES, WORKING_DIR, PORT, MAX_TERMINALS, WAIT_MESSAGE, TIMEOUT, returnedCode);

            userInterface.logInfo("=> PTI_Init");

            logApplicationInfo();

            if (returnedCode.getValue() == PTIRet.OK.getValue()) {
                ShortByReference connectionCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());

                do {
                    LibFunctions.connectionLoop(terminalId, model, mac, serialNumber, connectionCode);

//                   Ao identificar uma nova tentativa de conexão, a aplicação verifica se o terminal já está conectado.
//                    Caso contrário, a thread responsável por gerenciar o terminal conectado será iniciada.
                    if (connectionCode.getValue() == PTIRet.NEWCONN.getValue()) {
                        System.out.println("=> ConnectionLoop: " + connectionCode.getValue());

                        if (!isPOSConnected(terminalId)) {
                            POS pos = new POS(userInterface, terminalId, model, mac, serialNumber);

//                            Inicia execução no terminal recém conectado
                            Thread posThread = new Thread(pos);
                            posThread.start();

                            addConnectedPOS(pos);
                        } else {
                            userInterface.logInfo("TERMINAL " + Printer.format(terminalId) + " já está conectado!");
                        }
                    }

                    Thread.sleep(500);
                } while (keepAlive);

                if (!connectedPOS.isEmpty()) {
                    connectedPOS.forEach(terminalId -> LibFunctions.disconnect(((String)terminalId).getBytes(), 1, returnedCode));
                }

                Thread.sleep(1000);

                LibFunctions.end();
                userInterface.logInfo("=> PTI_End");
                userInterface.logInfo("Comunicação com a biblioteca foi encerrada.");
            } else {
                System.out.println("Exit");
                System.exit(-1);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Exibe no log da aplicação as informações gerais da aplicação.
     */
    private void logApplicationInfo() {
        userInterface.logInfo("\n======= INFO =======");
        userInterface.logInfo("COMPANY: " + COMPANY);
        userInterface.logInfo("VERSION: " + VERSION);
        userInterface.logInfo("CAPABILITIES: " + CAPABILITIES);
        userInterface.logInfo("WORKING DIR: " + WORKING_DIR);
        userInterface.logInfo("PORT: " + PORT);
        userInterface.logInfo("MAX TERMINALS: " + MAX_TERMINALS);
        userInterface.logInfo("====================\n");
    }

    private boolean isPOSConnected(byte[] terminalId) {
        return connectedPOS.contains(new String(terminalId));
    }

    private void addConnectedPOS(POS pos) {
        connectedPOS.add(pos.getTerminalId());
    }

    public void stopRunning() {
        this.keepAlive = false;
    }

    @Override
    public void run() {
        init();
    }
}
