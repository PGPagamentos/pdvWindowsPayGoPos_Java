package br.com.paygo.helper;

public class Printer {

    public static String format(byte[] value) {
        return new String(value).trim();
    }
}
