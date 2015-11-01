package sockets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.*;

public class FibonacciTexteingabe {
	
	private FibonacciServer server;
	private FibonacciClient client; 
	private Scanner input;
	private Commands commands;
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	private static final Pattern pattern =  Pattern.compile("java\\s+fibo\\s+<\\d{1,4}>");
	
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
					input.skip(Pattern.compile("berechne\\s"));
					int n = input.nextInt();
					server.calculateFibonnaci(n);
			} else if(command.matches("java\\s+fibo\\s+<\\"+IPADDRESS_PATTERN+"><\\d{1,4}>")) {
				System.out.println("true");
			} else {
				
			}
				//do nothing. "java\\s+fibo\\s+<\\"+IPADDRESS_PATTERN+"><\\d{1,4}>"
			}
		}
	
	public static void main(String[] args) throws NumberFormatException, IOException {

		
		FibonacciServer server = new FibonacciServer(8080);
		FibonacciClient client = new FibonacciClient("localhost", 8080);
		
		FibonacciTexteingabe eingabe = new FibonacciTexteingabe();
		eingabe.start();
		
	}
	

		
	}

