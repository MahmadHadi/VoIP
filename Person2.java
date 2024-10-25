import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Person2 {
    public static void main(String args[]) throws IOException {
        Socket s = new Socket("localhost", 1234);
        System.out.println("Connected to server.");

        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);

        while (true) {
            // Send message to Person1
            System.out.print("You: ");
            String msg = sc.nextLine();
            dout.writeUTF(msg);
            dout.flush();

            if (msg.equals("\0")) {
                System.out.println("Chat ended by client.");
                break;
            }

            // Receive response
            String response = din.readUTF();
            if (response.equals("\0")) {
                System.out.println("Chat ended by server.");
                break;
            }
            System.out.println("Person1: " + response);
        }

        // Close resources after exiting loop
        sc.close();
        din.close();
        dout.close();
        s.close();
    }
}
