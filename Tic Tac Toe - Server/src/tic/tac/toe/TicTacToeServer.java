
package tic.tac.toe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;



public class TicTacToeServer{
   int port;
  
   ServerSocket server;
   Socket socket;
   int ClientsNumber;
   boolean flag =true;
   Scanner scan = new Scanner(System.in); 
    
  //to make another object of class and make new connection on same server
   public TicTacToeServer(){}
   
   //to make new server
   public TicTacToeServer(int port)
    {
        this.port = port;
        
       try {
           server = new ServerSocket(port);
           System.out.println("\t\t\tServer Initilized\t \n");
         
       } catch (IOException ex) {
           System.out.println("\n\t\t\tAnother server is runing on same socket with port #: "+port+"\n\n");
           //System.out.println("Exit on Method TicTacToeServer(int port)");
           return;    
       }
        
        ListenForConenction(socket,server,ClientsNumber);
        
    }
   
   //Listen for new connection and make sperate thread for it and counting clients
    public void ListenForConenction(Socket socket ,ServerSocket server,int ClientsNumber) 
    {
       
        try{ 
       System.out.println("Waiting for connections ...");
       socket = server.accept();
       this.ClientsNumber=ClientsNumber++;
       Network client = new Network(socket,server,ClientsNumber);
       client.start();
        }
        catch(IOException IO)
        {
            System.out.println("Warning : IOException has been thrown ......... !!");
            System.out.println("Do you want to Restart Server ? (y/n)");
            if(scan.next().charAt(0)!='y')
                return;
            System.out.println("Restarting ....");
            ListenForConenction(socket,server,ClientsNumber);
        }
        
       
        
    }
    
  
}
