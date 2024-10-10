package struct;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer{
    // create a set of client handlers(so no duplicate clients are created)
    // create a function to broadcast message
    // create a server to remove the client

    


    private static Set<ClientHandler> clientHandlers = Collections.synchronizedSet(new HashSet<>()); // store clients

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started...");

        while(true){
            Socket socket = serverSocket.accept();
            System.out.println("connected");
            ClientHandler clientHandler = new ClientHandler(socket);
            addClient(clientHandler);
            new Thread(clientHandler).start();
        }
    }

    // create a function to broadcast a message
    public static synchronized void broadcast(String message,ClientHandler excludeCurrent){
        // System.out.println(message);
        // clientHandlers.add(excludeCurrent);
            for(ClientHandler client : clientHandlers){
                if (!client.equals(excludeCurrent)) {
                try {
                    client.sendMessage(message);
                } catch (Exception e) {
                    System.err.println("Error sending message to " + client.username);
                    e.printStackTrace();
                }
                    // out.println(message);
                }
            }
            // excludeCurrent.sendMessage(message);
    }

    // add a client
    public static synchronized void addClient(ClientHandler client){
        clientHandlers.add(client);
        broadcast("User "+client.username+" has joined the chat!", client);
    }

    // create a function to remove the client
    public static synchronized void removeClient(ClientHandler client){
        synchronized(clientHandlers){
            clientHandlers.remove(client);
        }
    }
    

}