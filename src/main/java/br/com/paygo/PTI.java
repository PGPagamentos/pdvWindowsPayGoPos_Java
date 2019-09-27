package br.com.paygo;

import br.com.paygo.enums.ApplicationProperties;
import br.com.paygo.enums.PTIRet;
import br.com.paygo.interop.LibFunctions;
import com.sun.jna.ptr.ShortByReference;

class PTI implements Runnable {

    private static final String COMPANY = "NTK SOLUTIONS";
    private static final String VERSION = ApplicationProperties.INSTANCE.getAppVersion();
    private static final String CAPABILITIES = "0";
    private static final String WORKING_DIR = ".";
    private static final short PORT = 10000;
    private static final short MAX_TERMINALS = 50;
    private static final String WAIT_MESSAGE = "APLICACAO EXEMPLO";
    private static final short TIMEOUT = 0;

    private boolean keepAlive;
    private byte[] terminalId;
    private byte[] model;
    private byte[] mac;
    private byte[] serialNumber;

    PTI() {
        keepAlive = true;
        terminalId = new byte[20];
        model = new byte[20];
        mac = new byte[20];
        serialNumber = new byte[25];
    }

    void init() throws InterruptedException {
        ShortByReference returnedCode = new ShortByReference(PTIRet.OK.getValue());

        LibFunctions.init(COMPANY, VERSION, CAPABILITIES, WORKING_DIR, PORT, MAX_TERMINALS, WAIT_MESSAGE, TIMEOUT, returnedCode);

        System.out.println("=> Init");

        if (returnedCode.getValue() == PTIRet.OK.getValue()) {
            ShortByReference connectionCode = new ShortByReference(PTIRet.INTERNAL_ERR.getValue());

            do {
                LibFunctions.connectionLoop(terminalId, model, mac, serialNumber, connectionCode);

                if (connectionCode.getValue() == PTIRet.NEWCONN.getValue()) {
                    System.out.println("=> ConnectionLoop: " + connectionCode.getValue());

                    POS pos = new POS(terminalId, model, mac, serialNumber);

                    Thread posThread = new Thread(pos);
                    posThread.start();
                }

                Thread.sleep(500);
            } while (keepAlive);
        } else {
            System.out.println("Exit");
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        try {
            init();
        } catch (InterruptedException e) {

        }
    }
}
