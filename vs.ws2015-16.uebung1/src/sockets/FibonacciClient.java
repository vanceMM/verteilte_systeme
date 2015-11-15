package sockets;

import java.net.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;
import java.util.regex.Pattern;

public class FibonacciClient {
    private static FibonacciClient client;
	

		
		private Socket server;
		private int n;
		private int result;
		private Scanner in;
		private Scanner input;
                private PrintWriter output;
                
    //private Commands commands;
    
		
		public FibonacciClient(String adresse, int port){
		
		try {
			
			in = new Scanner(System.in);
			server = new Socket(adresse, port);
			
			input = new Scanner (server.getInputStream());
                        output = new PrintWriter (server.getOutputStream(), true);
			
//			System.out.println("tippen sie eine Zahl n für die n-te Fibonacci Zahl ein");
			
                        
                        System.out.println("--Server-Client-Anwendung--");
		System.out.println("Type 'help' for a list of commands");
		
		String command="";
		while(!command.equals("ende")) {
			command = in.next();
				
			if(command.equals("help")) {
//				commands.allCommands();
			} else if(command.equals("berechne")) {
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
		}
		finally {
		      if ( server != null )
		        try { 
		        	server.close(); 
		        } catch ( IOException e ) { }
		    }
		}
                
              

                public void calculate(int n) {
                    /*if (n> 12) {
                        throw new IllegalArgumentException("-2");
                    }*/
                    output.println(n);
                    result = input.nextInt();
                    System.out.println(result);
                    
                }
                public static void main(String[] args) {
                    
                    
                    
                    client = new FibonacciClient(args[0], Integer.parseInt(args[1]));
                   // client = new FibonacciClient("localhost", 5678);
                    
                }
}
	

