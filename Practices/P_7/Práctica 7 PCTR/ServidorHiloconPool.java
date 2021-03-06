
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SERGIO
 */

public class ServidorHiloconPool implements Runnable {
    Socket enchufe;
    private int name;
    
    public ServidorHiloconPool(Socket s,int name)
    {
        enchufe = s;
        this.name=name;
    }

    public void run()
    {
    Date d = new Date();
    long inicCronom = System.currentTimeMillis(); //Preparacion del cronometro
    d.setTime(inicCronom); //Activacion del cronometro
    try{
        BufferedReader entrada = new BufferedReader(
                                    new InputStreamReader(
                                        enchufe.getInputStream()));
        String datos = entrada.readLine();
        int j;
        int i = Integer.valueOf(datos).intValue();
        for(j=1; j<=20; j++){
        System.out.println("El hilo " + name + " escribiendo el dato " + i);
        }
        enchufe.close();
        System.out.println("El hilo " + name + "cierra su conexion...");
    } catch(Exception e) {System.out.println("Error...");}
    long finCronom = System.currentTimeMillis(); //Pausa del cronometro
    d.setTime(finCronom);
    System.out.println("Tiempo: " + (finCronom - inicCronom) + " milisegundos");
    }

public static void main (String[] args)
{
    int i=0;
    int puerto = 2001;
    ExecutorService exe = Executors.newCachedThreadPool();
        try{
            ServerSocket chuff = new ServerSocket (puerto, 3000);

            while (true){
                System.out.println("Esperando solicitud de conexion...");
                Socket cable = chuff.accept();

                System.out.println("Recibida solicitud de conexion...");
                exe.execute(new ServidorHiloconPool(cable,i));
                i++;
            }
      } catch (Exception e)
        {System.out.println("Error en sockets...");}
}

}
