package sockets;

public class Commands {
	
	private final String[] commands = {"help", "berechne","ende"};
	
	
	public Commands() {
		
	}
	
	public void allCommands() {
		
                for(String command : commands) {
            System.out.print(command + "  ");
        }
        System.out.println();
	}
}
