package br.com.paygo.enums;

public enum PTIStat {
    IDLE,        // Terminal está on-line e aguardando por comandos.
    BUSY,        // Terminal está on-line, porém ocupado processando um comando
    NOCONN,      // Terminal está offline.
    WAITRECON;  // Terminal está off-line. A transação continua sendo executada e após sua finalização, o terminal tentará efetuar a reconexão automaticamente.
}
