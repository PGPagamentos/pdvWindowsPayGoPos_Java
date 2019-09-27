package br.com.paygo.enums;

import java.util.Arrays;

/**
 * Códigos de Erro de Retorno da Biblioteca
 */
public enum PTIRet {
    INTERNAL_ERR((short)99),      //Erro desta aplicação
    OK((short)0),                //Operação bem-sucedida
    INVPARAM((short)-2001),      //Parâmetro inválido informado à função.
    NOCONN((short)-2002),        //O terminal está offline
    BUSY((short)-2003),          //O terminal está ocupado processando outro comando.
    TIMEOUT((short)-2004),       //Usuário falhou ao pressionar uma tecla durante o tempo especificado
    CANCEL((short)-2005),        //Usuário pressionou a tecla [CANCELA].
    NODATA((short)2006),         //Informação requerida não disponível
    BUFOVRFLW((short)-2007),     //Dados maiores que o tamanho do buffer fornecido
    SOCKETERR((short)-2008),     //Impossibilitado de iniciar escuta das portas TCP especificadas
    WRITEERR((short)-2009),      //Impossibilitado de utilizar o diretório especificado
    EFTERR((short)-2010),        //A operação financeira foi completada, porém falhou
    INTERNALERR((short)-2011),   //Erro interno da biblioteca de integração
    PROTOCOLERR((short)-2012),   //Erro de comunicação entre a biblioteca de integração e o terminal
    SECURITYERR((short)-2013),   //A função falhou por questões de segurança
    PRINTERR((short)-2014),      //Erro na impressora
    NOPAPER((short)-2015),       //Impressora sem papel
    NEWCONN((short)-2016),       //Novo terminal conectado
    NONEWCONN((short)-2017),     //Sem recebimento de novas conexões.
    NOTSUPPORTED((short)-2057),  //Função não suportada pelo terminal.
    CRYPTERR((short)-2058);      //Erro na criptografia de dados (comunicação entre a biblioteca de integração e o terminal).

    private final short value;

    PTIRet(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    public static PTIRet valueOf(short value) throws Exception {
        return Arrays.stream(values()).filter(ptiRet -> ptiRet.value == value).findFirst().orElseThrow(() -> new Exception("Tipo de dado de retorno não mapeado (" + value + ")."));
    }
}
