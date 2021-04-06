package sample;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Socket clientSocket = null;
    public static ServerThread[] serverThreads;
    public ServerSocket serverSocket = null;
    private int MAXPLAYERS = 30;

    public Server(){
        System.out.println("Starting tic tac toe Server.");

        //open server socket to accept new connections
        try {
            serverSocket = new ServerSocket(8080);
            //array of threads, max == MAXPLAYERS
            serverThreads = new ServerThread[MAXPLAYERS];
            while(true){
                System.out.println("Waiting for a connection");
                clientSocket = serverSocket.accept();
                //assign sockets to players
                for (int i = 0; i < MAXPLAYERS; i++){
                    if (serverThreads[i] == null){
                        serverThreads[i] = new ServerThread(clientSocket);
                        serverThreads[i].start();
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Server server = new Server();
    }



}
