package mailbox;

/**
 * Created by valentin on 14/12/15.
 */
import javax.json.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    final static int PORT = 8090;
    protected long sequenceNumber;
    protected DataInputStream dataInputStream;
    protected DataOutputStream dataOutputStream;
    protected Thread listenerThread;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    public Client() {
        sequenceNumber = (long)(Math.random() * 1000);
    }

    public void run() {
        Socket socket = getConnection();
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        login();

        ClientListener listener = new ClientListener(socket);
        listenerThread = new Thread(listener);
        listenerThread.start();
        commandLoop();

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Socket getConnection() {
        Scanner in = new Scanner(System.in);
        System.out.print("IP address of the server: ");
        String ip = in.next();
        Socket socket;
        try {
            socket = new Socket(ip, PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return socket;
    }

    private boolean login() {
        // Wait for welcome message of server:
        JsonReader reader = Json.createReader(dataInputStream);
        JsonObject obj = reader.readObject();
        int statuscode = obj.getInt("statuscode");
        if (statuscode == 200) {
            System.out.println("Server welcome message: " + obj.getJsonArray("response").getString(0));
        } else {
            throw new RuntimeException("No welcome message from server.");
        }

        // Get username:
        Scanner in = new Scanner(System.in);
        while(true) {
            // Send a username:
            System.out.print("Username: ");
            String name = in.next();
            JsonObjectBuilder request = Json.createObjectBuilder();
            request.add("sequence", sequenceNumber++);
            request.add("command", "login");
            JsonArrayBuilder params = Json.createArrayBuilder();
            params.add(name);
            request.add("params", params);
            JsonWriter writer = Json.createWriter(dataOutputStream);
            writer.write(request.build());

            // Get response if name is available:
            reader = Json.createReader(dataInputStream);
            obj = reader.readObject();
            statuscode = obj.getInt("statuscode");
            switch (statuscode) {
                case 204:
                    System.out.println("Your are now logged in as " + name + ".");
                    return true;
                case 400:
                    System.out.println("Name is not available.");
                    break;
                default:
                    System.out.println("Server responded with statuscode " + statuscode + ".");
            }
        }
    }

    private void commandLoop() {
        Scanner in = new Scanner(System.in);
        while (true) {
            String command = in.nextLine();
            if (command.equals("exit")) {
                System.out.println("Loggin out...");
                logout();
                return;
            }
            String[] words = command.split(" ");
            if (words.length < 1) {
                System.out.println("Please enter a command.");
                continue;
            }
            JsonObjectBuilder request = Json.createObjectBuilder();
            request.add("sequence", sequenceNumber++);
            request.add("command", words[0]);
            JsonArrayBuilder params = Json.createArrayBuilder();
            for (int i = 1; i < (words.length); i++) {
                params.add(words[i]);
            }
            request.add("params", params);
            JsonWriter writer = Json.createWriter(dataOutputStream);
            writer.write(request.build());
        }
    }

    private void logout() {
        JsonObjectBuilder request = Json.createObjectBuilder();
        request.add("sequence", sequenceNumber++);
        request.add("command", "exit");
        JsonArrayBuilder params = Json.createArrayBuilder();
        request.add("params", params);
        JsonWriter writer = Json.createWriter(dataOutputStream);
        writer.write(request.build());
        try {
            listenerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
