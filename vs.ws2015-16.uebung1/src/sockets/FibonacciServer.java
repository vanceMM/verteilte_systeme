package sockets;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;

public class FibonacciServer {
	
	public static void main (String[] args) throws IOException {
		
		ServerSocket server = new ServerSocket(8080);
		
		while (true) {
			
			Socket client = null;
			
			try {
				//horcht auf Verbindung mit Client und erstellt neuen Socket
				client = server.accept();
				handleConnection(client);
			} catch(IOException e) {
				e.printStackTrace();
			}
			finally {
		        if ( client != null )
		          try { client.close(); } catch ( IOException e ) { }
		      }
		}
		
	}
	/*
	 * Methode zum berechnen der n-ten Fibonacci Zahl
	 * @param n die n-te Zahl
	 */
	private static int calculateFibonnaci(int n) {
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
	private static void handleConnection( Socket client ) throws IOException {
		
		Scanner input = new Scanner (client.getInputStream());
		PrintWriter output = new PrintWriter (client.getOutputStream(), true);
		

		String fibN = input.nextLine();

		output.println(calculateFibonnaci(new Integer(fibN)));
		
	}
}
