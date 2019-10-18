package br.com.paygo.interop;

import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;

class PTILib {

    private static final String LIB_NAME = "PTI_DLL.dll";
    private PTILibMap libInterface;

    static {
        System.setProperty("jna.debug_load", "true");
        System.setProperty("jna.debug_load.jna", "true");
    }

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

    void getData(byte[] terminalId, byte[] promptMessage, byte[] format, short minLength, short maxLength,
                 int startFromLeft, int alphanumericCharacters, int hasMask,
                 short timeout, byte[] data, short startLine, ShortByReference ret) {
        libInterface.PTI_GetData(terminalId, promptMessage, format, minLength, maxLength, startFromLeft,
                alphanumericCharacters, hasMask, timeout, data, startLine, ret);
    }

    void startTransaction(byte[] terminalId, short operation, ShortByReference ret) {
        libInterface.PTI_EFT_Start(terminalId, operation, ret);
    }

    void addParam(byte[] terminalId, short param, byte[] paramValue, ShortByReference ret) {
        libInterface.PTI_EFT_AddParam(terminalId, param, paramValue, ret);
    }

    void executeTransaction(byte[] terminalId, ShortByReference ref) {
        libInterface.PTI_EFT_Exec(terminalId, ref);
    }

    void printContent(byte[] terminalId, byte[] message, ShortByReference ret) {
        libInterface.PTI_Print(terminalId, message, ret);
    }

    void printCodeBar(byte[] terminalId, byte[] code, short symbology, ShortByReference ret) {
        libInterface.PTI_PrnSymbolCode(terminalId, code, symbology, ret);
    }

    void printEmptySpace(byte[] terminalId, ShortByReference ret) {
        libInterface.PTI_PrnFeed(terminalId, ret);
    }

    void printReceipt(byte[] terminalId, short receiptCopies, ShortByReference ret) {
        libInterface.PTI_EFT_PrintReceipt(terminalId, receiptCopies, ret);
    }

    void beep(byte[] terminalId, short beep, ShortByReference ret) {
        libInterface.PTI_Beep(terminalId, beep, ret);
    }

    void confirmTransaction(byte[] terminalId, short status, ShortByReference ret) {
        libInterface.PTI_EFT_Confirm(terminalId, status, ret);
    }

    void getInfo(byte[] terminalId, short info, short length, byte[] value, ShortByReference ret) {
        libInterface.PTI_EFT_GetInfo(terminalId, info, length, value, ret);
    }
}
