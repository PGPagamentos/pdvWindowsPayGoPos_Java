package br.com.paygo.interop;

import com.sun.jna.ptr.ShortByReference;

public class LibFunctions {
    static private PTILib ptiLib = new PTILib();

    public static void init(String company, String version, String capabilities, String workingDir, short tcpPort, short maxTerminals, String waitMessage, short timeout, ShortByReference ret) {
        ptiLib.init(company, version, capabilities, workingDir, tcpPort, maxTerminals, waitMessage, timeout, ret);
    }

    public static void connectionLoop(byte[] terminalId, byte[] model, byte[] MAC, byte[] serialNumber, ShortByReference ret) {
        ptiLib.connectionLoop(terminalId, model, MAC, serialNumber, ret);
    }

    public static void displayMessage(byte[] terminalId, String message, ShortByReference ret) {
        ptiLib.displayMessage(terminalId, message, ret);
    }

    public static void end() {
        ptiLib.end();
    }

    public static void waitKeyPress(byte[] terminalId, int timeout, ShortByReference key, ShortByReference ret) {
        ptiLib.waitKeyPress(terminalId, (short) timeout, key, ret);
    }

    public static void createMenu(byte[] terminalId,  ShortByReference ret) {
        ptiLib.createMenu(terminalId, ret);
    }

    public static void addMenuOption(byte[] terminalId, String optionMessage, ShortByReference ret) {
        ptiLib.addMenuOption(terminalId, optionMessage, ret);
    }

    public static void displayMenu(byte[] terminalId, String promptMessage, int timeout, ShortByReference selectedOptionIndex, ShortByReference ret) {
        ptiLib.displayMenu(terminalId, promptMessage.getBytes(), (short) timeout, selectedOptionIndex, ret);
    }

    public static void checkStatus(byte[] terminalId, ShortByReference status, byte[] model, byte[] mac, byte[] serialNumber, ShortByReference ret) {
        ptiLib.checkStatus(terminalId, status, model, mac, serialNumber, ret);
    }

    public static void clearKeys(byte[] terminalId, ShortByReference ret) {
        ptiLib.clearKeys(terminalId, ret);
    }

    public static void disconnect(byte[] terminalId, ShortByReference ret) {
        ptiLib.disconnect(terminalId, (short) 0, ret);
    }
}
