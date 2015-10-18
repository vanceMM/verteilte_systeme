package sockets;

import java.net.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;

public class FibonacciClient {
	
	public static void main(String[] args) {
		
		Socket server = null;
		int n;
		int result ;
		
		
		while (true){
		try {
			
			Scanner in = new Scanner(System.in);
			server = new Socket("localhost", 8080);
			
			Scanner input = new Scanner (server.getInputStream());
			PrintWriter output = new PrintWriter (server.getOutputStream(), true);
			
			System.out.println("tippen sie eine Zahl n für die n-te Fibonacci Zahl ein");
			
			if(!in.hasNextInt()){
				System.out.println("keine Zahl");
			} else if(in.equals("")){
				System.out.println("leerer String");
			}
			else
			{
			n = in.nextInt();

			output.println(n);
			try {
			result = input.nextInt();
			System.out.println(result);
			} catch (NoSuchElementException e) {
				System.out.println(e);
			}
			
			}
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
		      if ( server != null )
		        try { 
		        	server.close(); 
		        } catch ( IOException e ) { }
		    }
		}
		
	}
}
