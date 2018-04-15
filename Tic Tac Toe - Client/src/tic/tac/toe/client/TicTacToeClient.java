
package tic.tac.toe.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TicTacToeClient {
    String ip;
    int port;
    boolean complete=false;
    Socket socket;
    Scanner scan;
    int flag;
    DataInputStream DIS;
    DataOutputStream DOS;
    private char grid[][] = new char[3][3];
    private int place[] = new int[9];
  
        TicTacToeClient(String ip ,int port){
        this.ip=ip;
        this.port=port;
        
        System.out.println("\t\t\t Welcome in Tic Tac Toe ");
        System.out.println("Connecting to Server...");
        connect();
        System.out.println("Connected");
        System.out.println("Choose Your Opponent : ");
        System.out.println("[1] Server ");
        System.out.println("[2] Player ");
        System.out.print("Your Choice : \n");
          
        
        
      
    }
        
        public void connect()
        {
        scan = new Scanner(System.in);
             try{
        socket = new Socket(ip,port);
        
        }
        catch(IOException IO)
        {
           //System.out.println("Warning : IOException has been thrown ......... !!");
            System.out.println("Server Not Found ......... !!");
            System.out.println("Do you want to Reconnect Client ? (y/n)");
            if(scan.next().charAt(0)!='y')
                return;
            System.out.println("Reconnecting ....");
            connect();
        }
             
             
        }
        
         void printGrid() throws IOException{
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(DIS.readUTF());
               
                
            }
            System.out.println("");
        }
    }
         public void win() throws IOException
         {
             char win = DIS.readChar();
             if(win!='d')
             System.out.println("The Winner is: "+win);
             else
                 System.out.println("The Game Ended in Draw !!");
         }
    public void Play()
    {
       
       
      
             try {
                 
                 
                 
                 DIS = new DataInputStream(socket.getInputStream());
                 DOS = new DataOutputStream(socket.getOutputStream());

//                     //Player Choice is sent to server
                  DOS.writeInt(scan.nextInt());
                 } catch (IOException ex) {
                     Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 //----------------------------------------------------
                 try {
                    
                     //print Grid
                    printGrid();
                    flag = DIS.readInt();
                 
                    
                    while(true)
                    {
                       if(flag==1&&!complete)
                       {
                           do{
                           System.out.print("Please Choose number : ");
                           DOS.writeInt(scan.nextInt());
                           }while(!DIS.readBoolean());
                           printGrid();
                           complete = DIS.readBoolean();
                           flag=0;
                       }
                       else if (flag==0 &&!complete)
                       {
                           System.out.println("Please Wait for another player ......");
                           printGrid();
                           flag=DIS.readInt();
                           complete = DIS.readBoolean();
                           
                       }
                      if(complete)break;
                    }
                    win();
                    
                 } catch (IOException ex) {
                     Logger.getLogger(TicTacToeClient.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 
                 
                 
                 
             } 

       
       
        
         
      
    }
    

