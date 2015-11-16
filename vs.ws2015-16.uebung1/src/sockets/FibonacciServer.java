package sockets;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FibonacciServer {

    private ServerSocket server;
    private static DataInputStream input;
    private static DataOutputStream output;

    /*
     * Methode zum berechnen der n-ten Fibonacci Zahl
     * @param n die n-te Zahl
     */
    public static int calculateFibonnaci(int n) {
        if (n > 11) {
            return -2;
        }
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return calculateFibonnaci(n - 1) + calculateFibonnaci(n - 2);
        }

    }

    /*
     * Methode für das Protokoll der Verbindung
     */
    public static void handleConnection(Socket client) throws IOException {

        input = new DataInputStream(client.getInputStream());
        output = new DataOutputStream(client.getOutputStream());
        System.out.println("wartet");
        try {
            int fibN = input.readInt();
             System.out.println(fibN);
            output.writeInt(calculateFibonnaci(fibN));
        } catch (NumberFormatException e) {
            output.writeInt(-1);
        }
            
    }

    public static void main(String[] args) throws IOException {
        System.out.println("server start...");

        ServerSocket server = new ServerSocket(5678);
        while (true) {
            Socket client = null;
            try {
               
                //horcht auf Verbindung mit Client und erstellt neuen Socket
                System.out.println("server has started...");
                client = server.accept();
                System.out.println("client verbunden");
                handleConnection(client);
              
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if (client != null) {
                    try {
                        System.out.println("schließe die verbindung");
                        client.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
