package struct;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    protected String username;
    // private Set<ClientHandler> clientHandlers;

    // create a constructor to intialise private variables
    public ClientHandler(Socket socket) throws IOException{
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(),true);
        // this.clientHandlers.add(clientHandler);
    }

    @Override
    public void run(){
        String message;
        try{
            out.println("Enter username");
            username=in.readLine();
            ChatServer.broadcast("User "+username+" has joined!",this);
            try{
                while((message =in.readLine()) != null){
                    System.out.println("Recieved from client "+username+ " : "+message);
                    ChatServer.broadcast(username+" : "+message, this);
                }
            } catch(IOException error){
            System.out.println("Error in recieving message");
        }
        } catch(IOException error){
            System.out.println("Error in recieving message");
        } finally{
            try{
                socket.close();
                
            } catch(IOException e){
                e.printStackTrace();
            }
            ChatServer.removeClient(this);
        }
    }

    public synchronized void sendMessage(String message){
            out.println(message);
            out.flush();
    }
}
