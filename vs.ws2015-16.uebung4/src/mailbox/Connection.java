package mailbox;

/**
 * Created by valentin on 04/01/16.
 */
import javax.json.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Connection implements Runnable {

    protected Server server;
    protected Socket socket;
    protected String username;
    protected ArrayList<String> inbox;
    protected DataInputStream dataInputStream;
    protected DataOutputStream dataOutputStream;

    public Connection(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.username = "";
        this.inbox = new ArrayList<>();
    }

    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            sendWelcomeMessage();
            commandLoop();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendWelcomeMessage() {
        sendSingleResponse(0, 200, "Hello Client!");
    }

    private void commandLoop() {
        while (true) {
            JsonReader reader;
            try {
                reader = Json.createReader(dataInputStream);
            } catch (JsonException e) {
                break;
            }
            JsonObject obj = reader.readObject();
            int sequenceNumber = obj.getInt("sequence");
            String command = obj.getString("command");
            System.out.println("Received command " + command + ".");
            switch (command) {
                case "exit":
                    System.out.println("Client exited.");
                    server.removeConnection(this);
                    sendSingleResponse(sequenceNumber, 200, "Bye Bye!");
                    return;
                case "login":
                    String req_username = obj.getJsonArray("params").getString(0);
                    if (server.nameIsAvailable(req_username)) {
                        System.out.println("User logged in as " + req_username + ".");
                        this.username = req_username;
                        sendResponse(sequenceNumber, 204, null);
                    } else {
                        sendSingleResponse(sequenceNumber, 400, null);
                    }
                    break;
                case "time":
                    sendSingleResponse(sequenceNumber, 200, (new java.util.Date()).toString());
                    break;
                case "inbox":
                    sendResponse(sequenceNumber, 200, inbox);
                    break;
                case "who":
                    sendResponse(sequenceNumber, 200, server.getUsernames());
                    break;
                case "ls":
                    try {
                        String path = obj.getJsonArray("params").getString(0);
                        File f = new File(path);
                        ArrayList<String> names = new ArrayList<>(Arrays.asList(f.list()));
                        sendResponse(sequenceNumber, 200, names);
                    } catch (Exception e) {
                        e.printStackTrace();
                        sendResponse(sequenceNumber, 400, null);
                    }
                    break;
                case "msg":
                    String msg = obj.getJsonArray("params").getString(0);
                    String client = obj.getJsonArray("params").getString(1);
                    boolean success = server.sendMessage(username, client, msg);
                    if (success) {
                        sendResponse(sequenceNumber, 204, null);
                    } else {
                        sendResponse(sequenceNumber, 404, null);
                    }
                    break;
                default:
                    sendResponse(sequenceNumber, 501, null);
            }
        }
    }

    private void sendResponse(int sequence, int statuscode, ArrayList<String> response_list) {
        JsonObjectBuilder server_response = Json.createObjectBuilder();
        server_response.add("sequence", sequence);
        server_response.add("statuscode", statuscode);
        JsonArrayBuilder response = Json.createArrayBuilder();
        if (response_list != null) {
            for (String s: response_list) {
                response.add(s);
            }
        }
        server_response.add("response", response);
        JsonWriter writer = Json.createWriter(dataOutputStream);
        writer.write(server_response.build());
    }

    private void sendSingleResponse(int sequence, int statuscode, String response_string) {
        ArrayList<String> list = new ArrayList<>();
        list.add(response_string);
        sendResponse(sequence, statuscode, list);
    }

    public void sendMessage(String sender, String message) {
        String content = "Message from " + sender + ": " + message;
        sendSingleResponse(0, 200, content);
    }
}
