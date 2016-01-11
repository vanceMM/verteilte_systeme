package mailbox;

/**
 * Created by valentin on 04/01/16.
 */
import javax.json.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientListener implements Runnable {

    protected Socket socket;

    public ClientListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream dataInputStream;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            JsonReader reader;
            try {
                reader = Json.createReader(dataInputStream);
            } catch (JsonException e) {
                System.out.println("Connection closed.");
                return;
            }
            JsonObject obj = reader.readObject();
            int statuscode = obj.getInt("statuscode");
            switch (statuscode) {
                case 200:
                    JsonArray response = obj.getJsonArray("response");
                    for (JsonValue value: response) {
                        String s = value.toString();
                        System.out.print(" " + s);
                    }
                    System.out.println();
                    break;
                case 204:
                    System.out.println("204: OK without response.");
                    break;
                default:
                    System.out.println("Server responded with statuscode " + statuscode + ".");
            }
        }
    }
}
