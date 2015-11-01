package sockets;

public class FibonacciTexteingabeDemo {
	
	private FibonacciTexteingabe eingabe;
	
	public FibonacciTexteingabeDemo() {
		
		eingabe = new FibonacciTexteingabe();
	}
	
	public void showInterface() {
		eingabe.start();
	}
	public static void main(String[] args) {
		
		FibonacciTexteingabeDemo demo = new FibonacciTexteingabeDemo();
		demo.showInterface();
	}
}
