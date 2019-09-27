package br.com.paygo;

public class App 
{
    public static void main( String[] args )
    {
        PTI pti = new PTI();
        Thread ptiThread = new Thread(pti);
        ptiThread.start();
    }
}
