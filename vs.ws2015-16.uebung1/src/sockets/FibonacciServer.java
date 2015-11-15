package sockets;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FibonacciServer {

	
	private ServerSocket server;
	private static Scanner input;


	
	/*
	 * Methode zum berechnen der n-ten Fibonacci Zahl
	 * @param n die n-te Zahl
	 */
	public static int calculateFibonnaci(int n) {
		if(n> 11) {
			throw new IllegalArgumentException("");
		}
		if(n == 0)
	        return 0;
	    else if(n == 1)
	      return 1;
	   else
	      return calculateFibonnaci(n - 1) + calculateFibonnaci(n - 2);
		
	}
	
	/*
	 * Methode für das Protokoll der Verbindung
	 */
	public static void handleConnection( Socket client ) throws IOException {
		
		input = new Scanner (client.getInputStream());
		PrintWriter output = new PrintWriter (client.getOutputStream(), true);
		System.out.println("scanner");
		try {
		int fibN = input.nextInt();
                
                    System.out.println(fibN);
		output.println(calculateFibonnaci(new Integer(fibN)));
		} catch (NoSuchElementException e){
			output.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("server start...");
		
		ServerSocket server = new ServerSocket(5678);
                                     while ( true ) {
					Socket client = null;
					try {
						//horcht auf Verbindung mit Client und erstellt neuen Socket
						client = server.accept();
                                                System.out.println("client verbunden");
						handleConnection(client);
						System.out.println("server has started...");
					} catch(IOException|IllegalArgumentException e) {
						e.printStackTrace();
					}
					finally {
				        if ( client != null )
				          try { client.close(); } catch ( IOException e ) { }
				      }
                                     }
	}
}
