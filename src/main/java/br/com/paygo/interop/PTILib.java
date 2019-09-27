package br.com.paygo.interop;

import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;

class PTILib {

    private static final String LIB_NAME = "PTI_DLL.dll";
    private PTILibMap libInterface;

    PTILib() {
        Native.setProtected(true);
        this.libInterface = Native.load(LIB_NAME, PTILibMap.class);
    }

    void init(String company, String version, String capabilities, String workingDir, short tcpPort, short maxTerminals, String waitMessage, short timeout, ShortByReference ret) {
        libInterface.PTI_Init(company, version, capabilities, workingDir, tcpPort, maxTerminals, waitMessage, timeout, ret);
    }

    void connectionLoop(byte[] terminalId, byte[] model, byte[] MAC, byte[] serialNumber, ShortByReference ret) {
        libInterface.PTI_ConnectionLoop(terminalId, model, MAC, serialNumber, ret);
    }

    void checkStatus(byte[] terminalId, ShortByReference status, byte[] model, byte[] mac, byte[] serialNumber, ShortByReference ret) {
        libInterface.PTI_CheckStatus(terminalId, status, model, mac, serialNumber, ret);
    }

    void displayMessage(byte[] terminalId, String message, ShortByReference ret) {
        libInterface.PTI_Display(terminalId, message, ret);
    }

    void end() {
        libInterface.PTI_End();
    }

    void waitKeyPress(byte[] terminalId, short timeout, ShortByReference key, ShortByReference ret) {
        libInterface.PTI_WaitKey(terminalId, timeout, key, ret);
    }

    void createMenu(byte[] terminalId, ShortByReference ret) {
        libInterface.PTI_StartMenu(terminalId, ret);
    }

    void addMenuOption(byte[] terminalId, String optionMessage, ShortByReference ret) {
        libInterface.PTI_AddMenuOption(terminalId, optionMessage, ret);
    }

    void displayMenu(byte[] terminalId, byte[] promptMessage, short timeout, ShortByReference selectedOptionIndex, ShortByReference ret) {
        libInterface.PTI_ExecMenu(terminalId, promptMessage, timeout, selectedOptionIndex, ret);
    }

    void clearKeys(byte[] terminalId, ShortByReference ret) {
        libInterface.PTI_ClearKey(terminalId, ret);
    }

    void disconnect(byte[] terminalId, short powerDelay, ShortByReference ret) {
        libInterface.PTI_Disconnect(terminalId, powerDelay, ret);
    }
}
