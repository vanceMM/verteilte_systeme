package sockets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputTest {
	
	public static void main(String[] args) {
		
		 String str = "fibo <ip> calculate <port>";
		    Pattern pattern = Pattern.compile("<(.*?)>");
		    Matcher matcher = pattern.matcher(str);
		    while (matcher.find()) {
		        System.out.println(matcher.group(1));
		    }
	}
}
