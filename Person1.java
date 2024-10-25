import java.io.*;
import java.net.*;

public class Person1 {
    public static void main(String args[]) throws IOException {
        ServerSocket ss = new ServerSocket(1234);
        System.out.println("Server started, waiting for connection...");

        Socket s = ss.accept();
        System.out.println("Client connected.");

        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

        while (true) {
            // Receive message from Person2
            String msg = din.readUTF();
            if (msg.equals("\0")) {
                System.out.println("Chat ended by client.");
                break;
            }
            System.out.println("Person2: " + msg);

            // Send response
            System.out.print("You: ");
            String response = new BufferedReader(new InputStreamReader(System.in)).readLine();
            dout.writeUTF(response);
            dout.flush();

            if (response.equals("\0")) {
                System.out.println("Chat ended by server.");
                break;
            }
        }

        // Close resources after exiting loop
        din.close();
        dout.close();
        s.close();
        ss.close();
    }
}
