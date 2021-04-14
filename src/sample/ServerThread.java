package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
    Socket socket = null;
    String board = "0,0,0,0,0,0,0,0,0";
    PrintWriter out = null;
    BufferedReader in = null;
    Boolean lookingForPartner = false;
    ServerThread partner = null;
    Boolean primaryThread = null;
    public static Boolean gameIsOver = false;
    int playerID;

    public ServerThread(Socket socket, int playerID){
        super();
        this.socket = socket;
        this.playerID = playerID;
        System.out.println(socket.isConnected());
        try {
            System.out.println("Establishing IO");
            //store the io streams
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Test after out");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Test after in");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        //IMPORTANT all clients are expected to send what type of game they want "CPU" or "PLAYER"
        //Immediately upon making connection
        /*
        Planning the back and forth between server and client
        1. Connection
        2. Client asks for cpu or player
        3. Server searches thru connected list for player not in game
        4. Server sends who's turn it is or if game is done
        5. Server sends current board
        6. Server waits for msg from player's who turn it is
        7. Client sends grid coord of move
        8. Loop back to step 4 until win or board is full
        9. Disconnect clients? (or new game? tbd)
        */

        //Reads what the user wants
        String line = null;
        try{
            line = in.readLine();
        } catch (Exception e){
            e.printStackTrace();
        }
        //test print
        System.out.println("The inputted line is '" + line + "'"); //Test read

        //take the initial line in from the client and determine what they want
        if (line.equals("CPU")){
            //todo create cpu game
        }
        //todo check this if line to make sure it is actually comparing to the line properly
        else if(line.equals("PLAYER")){
            lookingForPartner = true;
            ticTacToePvp();
        }



    }

    public void ticTacToePvp(){
        //while you dont have a partner search for one
        System.out.println("A player is searching");
        while (lookingForPartner){
            System.out.println("Entering the world of the while loop");
            //get the list of players active on the server
            ServerThread[] players = Server.serverThreads;
            for (ServerThread player : players){
                //if you have found a player you need to make them stop looking
                //you also need to stop looking and store that player as the partner
                //the thread which finds a player first becomes the primary thread
                //which will execute the game code
                if (player != this && player != null && player.lookingForPartner){
                    System.out.println("Entering the world of players");
                    player.lookingForPartner = false;
                    this.lookingForPartner = false;
                    this.partner = player;
                    partner.partner = this;
                    primaryThread = true;
                    partner.primaryThread = false;
                    break;
                }
            }
        }
        System.out.println("Player found!");
        //if thread isn't the main thread have it stay open until the game is done to allow
        //for io to it's client
        if (!primaryThread){
            while(!gameIsOver){
            //wait while the main thread handles everything
            }
            System.exit(0);
        }

        //keep track of who's turn it is
        int playerTurn = 1;
        //variable for taking in lines
        String line = null;
        //string[] for storing the player's move
        String playerMove = null;

        //run the game while it isn't over
        while (!gameIsOver){
            /*
            Behaviour expected from this point on is..
            1. Tell all clients who's turn it is or if game is over
            2. Client who's turn it is will send their turn while Client who's turn it isn't waits
               for the board update
            3. Both Clients get board update
            4. Repeat
            */
            //tell the client whether to wait for input from the partner or to get input from
            //its user

            if (playerTurn == 1){
                out.println("1");
                partner.out.println("2");
                System.out.println("Player 1's turn");
            } else {
                out.println("2");
                partner.out.println("1");
                System.out.println("Player 2's turn");
            }

            try{
                //read in the input from the player
                if (playerTurn == 1){
                    line = in.readLine();
                } else {
                    line = partner.in.readLine();
                }
                //player input accepted as single int coord
                playerMove = line;
                //the game board is split in to a list of strings in order to change a specific tile later
                String[] workingBoard = board.split(",");
                //the square to update is calculated starting in the top left corner and counting down
                //"1,0,0,0,0,0,0,0,0" x in top left
                //"0,0,0,0,1,0,0,0,0" x in middle
                //"0,0,2,0,1,0,0,0,0" x in middle

                board = line;
                System.out.println("new board = " + board);
                /*
                int squareToUpdate = Integer.parseInt(playerMove);
                //update the square
                workingBoard[squareToUpdate] = String.valueOf(playerTurn);
                if (playerTurn == 1){
                    playerTurn = 2;
                } else {
                    playerTurn = 1;
                }
                //join the updated board and store it in the board string
                board = String.join(",",workingBoard);
                //send the new board to both clients

                 */


                if (playerTurn == 1){
                    playerTurn = 2;
                    System.out.println("sending player 2 the board");
                    partner.out.println(board);
                } else {
                    playerTurn = 1;
                    System.out.println("sending player 1 the board");
                    out.println(board);
                }
                //todo code if game is over check
            }catch (Exception e) {
                e.printStackTrace();
                gameIsOver = true;
                partner.gameIsOver = true;
            }
        }
        out.println("3");
        partner.out.println("3");

    }





}
