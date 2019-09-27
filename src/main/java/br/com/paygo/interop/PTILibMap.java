package br.com.paygo.interop;

import br.com.paygo.enums.PTIBeep;
import br.com.paygo.enums.PTICnf;
import br.com.paygo.enums.PWInfo;
import br.com.paygo.enums.PWOper;
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

    void PTI_GetData(byte[] terminalId, String promptMessage, String format, short minLength, short maxLength,
                     boolean startFromLeft, boolean alphanumericCharacters, boolean hasMask,
                     short timeout, String data, short startLine, ShortByReference ret);

    void PTI_StartMenu(byte[] terminalId, ShortByReference ret);

    void PTI_AddMenuOption(byte[] terminalId, String optionMessage, ShortByReference ret);

    void PTI_ExecMenu(byte[] terminalId, byte[] promptMessage, short timeout, ShortByReference selectedOptionIndex, ShortByReference ret);

    void PTI_Beep(byte[] terminalId, PTIBeep beepType, ShortByReference ret);

    void PTI_Print(byte[] terminalId, String message, ShortByReference ret);

    void PTI_PrnFeed(byte[] terminalId, ShortByReference ret);

    void PTI_PrnSymbolCode(byte[] terminalId, String code, short codeSymbol, ShortByReference ret);

    void PTI_EFT_Start(byte[] terminalId, PWOper operation, ShortByReference ret);

    void PTI_EFT_AddParam(byte[] terminalId, PWInfo param, String value, ShortByReference ret);

    void PTI_EFT_Exec(byte[] terminalId, ShortByReference ret);

    void PTI_EFT_GetInfo(byte[] terminalId, PWInfo param, short bufferLength, String value, ShortByReference ret);

    void PTI_EFT_PrintReceipt(byte[] terminalId, short receiptCopies, ShortByReference ret);

    void PTI_EFT_Confirm(byte[] terminalId, PTICnf cnf, ShortByReference ret);
}
