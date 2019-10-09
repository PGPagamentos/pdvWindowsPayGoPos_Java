package br.com.paygo.interop;

import com.sun.jna.Library;
import com.sun.jna.ptr.ShortByReference;

public interface PTILibMap extends Library {
    void PTI_Init(String company, String version, String capabilities, String workingDir, short tcpPort, short maxTerminals, String waitMessage, short timeout, ShortByReference ret);

    void PTI_End();

    void PTI_ConnectionLoop(byte[] terminalId, byte[] model, byte[] MAC, byte[] serialNumber, ShortByReference ret);

    void PTI_CheckStatus(byte[] terminalId, ShortByReference status, byte[] model, byte[] MAC, byte[] serialNumber, ShortByReference ret);

    void PTI_Disconnect(byte[] terminalId, short powerDelay, ShortByReference ret);

    void PTI_Display(byte[] terminalId, String message, ShortByReference ret);

    void PTI_WaitKey(byte[] terminalId, short timeout, ShortByReference key, ShortByReference ret);

    void PTI_ClearKey(byte[] terminalId, ShortByReference ret);

    void PTI_GetData(byte[] terminalId, byte[] promptMessage, byte[] format, short minLength, short maxLength,
                     int startFromLeft, int alphanumericCharacters, int hasMask,
                     short timeout, byte[] data, short startLine, ShortByReference ret);

    void PTI_StartMenu(byte[] terminalId, ShortByReference ret);

    void PTI_AddMenuOption(byte[] terminalId, String optionMessage, ShortByReference ret);

    void PTI_ExecMenu(byte[] terminalId, byte[] promptMessage, short timeout, ShortByReference selectedOptionIndex, ShortByReference ret);

    void PTI_Beep(byte[] terminalId, short beepType, ShortByReference ret);

    void PTI_Print(byte[] terminalId, byte[] message, ShortByReference ret);

    void PTI_PrnFeed(byte[] terminalId, ShortByReference ret);

    void PTI_PrnSymbolCode(byte[] terminalId, byte[] code, short codeSymbol, ShortByReference ret);

    void PTI_EFT_Start(byte[] terminalId, short operation, ShortByReference ret);

    void PTI_EFT_AddParam(byte[] terminalId, short param, byte[] value, ShortByReference ret);

    void PTI_EFT_Exec(byte[] terminalId, ShortByReference ret);

    void PTI_EFT_GetInfo(byte[] terminalId, short param, short bufferLength, byte[] value, ShortByReference ret);

    void PTI_EFT_PrintReceipt(byte[] terminalId, short receiptCopies, ShortByReference ret);

    void PTI_EFT_Confirm(byte[] terminalId, short cnf, ShortByReference ret);
}
