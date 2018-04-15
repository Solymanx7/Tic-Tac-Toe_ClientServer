
package tic.tac.toe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Network extends Thread {
    
     TicTacToeServer Disconnected;
     Socket socket;
     ServerSocket server;
     String ClinetInfo;
     int ClientID;
     int ClientsNumber;
     int Total =1;
     DataInputStream in;
     DataOutputStream out;
     Game game =new Game();
    
     // initialize global variables and assign client to this thread with ClientID
    Network(Socket socket,ServerSocket server,int ClinetsNumber) throws IOException {
       
    this.ClientsNumber = ClinetsNumber;    
    this.socket = socket;
    this.server = server;
    ClientID = ClientsNumber;
    ClinetInfo = "ClinetID: "+"["+ClientsNumber+"]"+":"+currentThread().getId()+":"+socket.getLocalAddress()+":"+Integer.toString(socket.getLocalPort());
    
       
    }
    
    //This function handle connecting and disconnecting and open new channel for each client
    @Override
    public void run()
    {
       
         try {
             
             System.out.println("Client ["+ClientID+"]"+" Connected");
             //System.out.println(ClinetInfo);
             Channel();
         }
         catch (IOException ex) {
             System.out.println("Client "+"["+ClientID+"]"+" Disconnnected");
             Disconnected = new TicTacToeServer();
             ClientsNumber--;
             Disconnected.ListenForConenction(socket, server, ClientsNumber);
         } catch (InterruptedException ex) {
             Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
    
    //Establish Communication Input/Output channel and CreateAnotherConnection if Needed
    public void Channel() throws IOException, InterruptedException
    {
        
       InputStream input = socket.getInputStream();
       OutputStream output = socket.getOutputStream();
        
       in = new DataInputStream(input);
       out = new DataOutputStream(output);
       //out.writeUTF("Please Wait for server to configure the game");
       CreateAnotherConnection();
       //Put Game function Here
       
       
    }
    
    //Check User Choice 
    public void CreateAnotherConnection() throws IOException, InterruptedException
    {
        int UserChoice;
       
        
        //Take player Choice [Against Server or Player]
        UserChoice=in.readInt();
//        UserChoice = 2;
        //System.out.println(x);
        
        if(UserChoice==2)
        { 
            Total =2;
            //Create Object for another player
            TicTacToeServer se = new TicTacToeServer();
            //Check if there is another player or not
            if(ClientsNumber<2)
            {
                game.Game(in, out, ClientsNumber, Total);
                
                System.out.println("Please wait for another Player");
                se.ListenForConenction(socket, server, ClientsNumber);
            }
            else
            {
                game.Game(in, out, ClientsNumber, Total);
            }
        //Connect another Player
        
        }
        else{
            Total =1;
            Game g =new Game();
            g.Game(in, out, ClientsNumber, Total);
        }
        
    }
}
