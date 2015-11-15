package sockets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.*;

public class FibonacciTexteingabe {
	
	private static FibonacciServer server;
	private static FibonacciClient client; 
	private Scanner input;
	private Commands commands;

	
	public FibonacciTexteingabe() {
	
		input = new Scanner(System.in);
		commands = new Commands();
	}
	
	public void start() {
		
		System.out.println("--Server-Client-Anwendung--");
		System.out.println("Type 'help' for a list of commands");
		
		String command="";
		while(!command.equals("ende")) {
			command = input.nextLine();
				
			if(command.equals("help")) {
				commands.allCommands();
			} else if(command.equals("calculate")) {
					server.calculateFibonnaci(1);
//					input.skip(Pattern.compile("berechne\\s"));
//					int n = input.nextInt();
//					server.calculateFibonnaci(n);
			} else {
				
				//do nothing. "java\\s+fibo\\s+<\\"+IPADDRESS_PATTERN+"><\\d{1,4}>"
			}
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		FibonacciTexteingabe eingabe = new FibonacciTexteingabe();
		eingabe.start();

		client = new FibonacciClient("localhost", 5678);
		
		
		
		
	}
	

		
	}

