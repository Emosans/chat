package struct;
import java.io.*;
import java.net.*;

public class ChatClient {
    private static ClientHandler client;
    public static void main(String[] args) throws IOException{
        // create socket,reader,output printer, console input to sent to server
        Socket socket = new Socket("localhost",12345);
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

        new Thread(()->{
            String message;
            try{
                while((message=in.readLine())!=null){
                    System.out.println(message); // listen to messages from the server
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }).start();

        String input;
        while (socket.isConnected()) {
            System.out.println("Enter a message");
            input = consoleInput.readLine();
            out.println(input); // send user input to server
        }

        socket.close();
    }

}
