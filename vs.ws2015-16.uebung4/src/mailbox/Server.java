package mailbox;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class Server {

    final static int PORT = 8090;
    final static int MAX_CLIENTS = 5;
    ArrayList<Connection> connections;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    public Server() {
        connections = new ArrayList<>();
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for clients to connect...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected from " + socket.getInetAddress() + ".");
                if (connections.size() >= MAX_CLIENTS) {
                    System.out.println("Too many clients, can't accept another one.");
                    sendToManyClientsResponse(socket);
                    socket.close();
                    continue;
                }
                Connection connection = new Connection(this, socket);
                Thread thread = new Thread(connection);
                connections.add(connection);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendToManyClientsResponse(Socket socket) {
        DataOutputStream dataOutputStream;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        JsonObjectBuilder server_response = Json.createObjectBuilder();
        server_response.add("sequence", 0);
        server_response.add("statuscode", 503);
        JsonArrayBuilder response = Json.createArrayBuilder();
        response.add("Too many clients, can't accept another one.");
        server_response.add("response", response);
        JsonWriter writer = Json.createWriter(dataOutputStream);
        writer.write(server_response.build());
    }

    public boolean nameIsAvailable(String name) {
        for (Connection connection: connections) {
            if (connection.username.equals(name)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for (Connection connection: connections) {
            usernames.add(connection.username);
        }
        return usernames;
    }

    public boolean sendMessage(String sender, String receiver, String message) {
        for (Connection connection: connections) {
            if (connection.username.equals(receiver)) {
                connection.sendMessage(sender, message);
                return true;
            }
        }
        return false;
    }

    protected void removeConnection(Connection s) {
        connections.remove(s);
    }


}
