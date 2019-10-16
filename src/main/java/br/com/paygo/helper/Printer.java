package br.com.paygo.helper;

/**
 * Helper responsável por formatar as impressões de retornos da Biblioteca.
 */
public class Printer {

    public static String format(byte[] value) {
        return new String(value).trim();
    }
}
