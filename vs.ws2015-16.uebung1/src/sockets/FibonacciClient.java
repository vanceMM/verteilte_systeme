package sockets;

import java.net.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;
import java.util.regex.Pattern;

public class FibonacciClient {
    private String adresse;
    private int port;
    private static FibonacciClient client;
    private Socket server;
    private int n;
    private int result;
    private Scanner in;
    private DataInputStream input;
    private DataOutputStream output;
    private final String[] commands = {"help", "berechne", "ende"};

    public FibonacciClient(String adresse, int port) {
        
        this.adresse = adresse;
        this.port = port;
        
        try {

            in = new Scanner(System.in);
            

//			System.out.println("tippen sie eine Zahl n für die n-te Fibonacci Zahl ein");
            System.out.println("--Server-Client-Anwendung--");
            System.out.println("Type 'help' for a list of commands");

            String command = "";
            while (!command.equals("ende")) {
                command = in.next();

                if (command.equals("help")) {
                    allCommands();
                } else if (command.equals("berechne")) {
                    try {
                        int n = in.nextInt();
                        calculate(n);
                    } catch (NoSuchElementException e) {
                        System.out.println("-1");
                       
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        
                    }
                } else {

                    //do nothing. "java\\s+fibo\\s+<\\"+IPADDRESS_PATTERN+"><\\d{1,4}>"
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    System.out.println("socket in while schleife geschlossen");
                    server.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void calculate(int n) throws IOException {
        System.out.println("Verbindung Aufbau zum Server " + this.adresse+ ":" +this.port);
        server = new Socket(this.adresse, this.port);

            input = new DataInputStream(server.getInputStream());
 //           output = new PrintWriter(server.getOutputStream(), true);
            output = new DataOutputStream(server.getOutputStream());
        try {
          output.writeInt(n);
        result = input.readInt();
        if (result == -2) {
            System.err.println("-2 zahl außerhalb des gültigen bereiches");
        } else if (result == -1) {
            System.err.println("-1 zerwarte positive ganze zahl");
        } else {
            System.out.println(result);
        }
        } catch (IOException e) {
            
        } finally {
            System.out.println("socket in calculate geschlossen");
            server.close();
        }
        

    }

    public void allCommands() {

        for (String command : commands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            client = new FibonacciClient(args[0], 5678);

        } else {

            client = new FibonacciClient(args[0], Integer.parseInt(args[1]));
            // client = new FibonacciClient("localhost", 5678);
        }
    }
}
