
package tic.tac.toe;

import java.io.*;
import java.net.*;
import java.util.Random;


public class Game {
 
    static DataInputStream in1;
    static DataInputStream in2;
    static DataOutputStream out1;
    static DataOutputStream out2;
    Random rand = new Random();
    static int Total;
    static int ID;
    static int x;
    static int inp1,inp2;
    static boolean accepted;
    static char player;
      char winner = '\u0000';
    
    private char grid[][] = new char[3][3];
    private int place[] = new int[9];
    
    void printGrid() throws IOException{
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                out1.writeUTF("| "+grid[i][j]+" ");
            }
           
        }
    }
     void printGrid2() throws IOException{
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                
                out1.writeUTF("| "+grid[i][j]+" ");
                out2.writeUTF("| "+grid[i][j]+" ");
                
               
                
              
            }
           
        }
    }
     
     Game()
     {
         
     }
    public void Bot()
    {
        
        if(!checkComplete())
        {
            int num;
            if(checkPosition(5))
            {
                play(5,'o');
            }
            else if(checkPosition(1)||checkPosition(3)||checkPosition(7)||checkPosition(9))
            {
                do
                    num = rand.nextInt(9)+1;
                while(!( (num == 1 || num == 3 || num == 7 || num ==9) && (checkPosition(num)) ) );
                 play(num,'o');
            }
            else {
                //System.out.println("Third");
                    do
                    num = rand.nextInt(9)+1;
                    while(!checkPosition(num));
                     //System.out.println("Third If statment: "+num);
                    play(num,'o');
                    }
            
               
                }
        
    }
    public void Game(DataInputStream in , DataOutputStream out , int ID,int Total) throws IOException, InterruptedException   {
        this.Total =Total;
        this.ID = ID;
        
        if(Total==1)
        {
            in1=in;
            out1=out;
             printGrid();
             out1.writeInt(1); // send flag
             while(true)
             {
                  do{
                    inp1=in1.readInt();
                    //System.out.println(accepted);
                    accepted = checkPosition(inp1);
                    
                    out1.writeBoolean(accepted);
                        //System.out.println(accepted);
                    }while(!accepted);
                     play(inp1,'x');
                    printGrid();
                    out1.writeBoolean(checkComplete());
                    if(checkComplete())
                          out1.writeChar(winner);
                         Bot();
                    printGrid();
                    out1.writeInt(1); // send flag
                    out1.writeBoolean(checkComplete()); // send complete
                    if(checkComplete())
                          out1.writeChar(winner);
             }
           
            //System.out.println("Danger");
            //out1.writeUTF("Welcome Player 1 \n Game will start soon");
        }
        else if (Total==2)
        {
            if(ID==1)
            {
            in1=in;
            out1=out;
            player='x';
        
            }
            else if (ID==2)
            {
            in2=in;
            out2=out;
            player = 'o';
        
            
           
            printGrid2();
             out1.writeInt(1);
             out2.writeInt(0);
                while(true)
                {
                    
                    // this handle player 1 input/output
                    do{
                    inp1=in1.readInt();
                    accepted = checkPosition(inp1);
                    out1.writeBoolean(accepted);
                    }while(!accepted);
                    play(inp1,'x');
                    printGrid2();
                    out1.writeBoolean(checkComplete());
                    if(checkComplete())
                          out1.writeChar(winner);
                    out2.writeInt(1);
                    out2.writeBoolean(checkComplete());
                    if(checkComplete())
                          out2.writeChar(winner);
                  
                    
                   // this handle player 2 input/output
                    do{
                    inp2=in2.readInt();
                    accepted = checkPosition(inp2);
                    out2.writeBoolean(accepted);
                    }while(!accepted);
                    play(inp2,'o');
                    printGrid2();
                    out1.writeInt(1);
                    out1.writeBoolean(checkComplete());
                    if(checkComplete())
                          out1.writeChar(winner);
                    out2.writeBoolean(checkComplete());
                    if(checkComplete())
                          out2.writeChar(winner);
                    
                    
                    
                }
            }
          
            //out2.writeUTF("Welcome Player 2 \n Game will start soon");
            
        }
      
       
        
        
    }
    
  
    void play(int input,char player){
        addToPlace(input);
        if(input == 1)
            addToGrid(2,0, player);
        else if(input == 2)
            addToGrid(2,1, player);
        else if(input == 3)
            addToGrid(2,2, player);
        else if(input == 4)
            addToGrid(1,0, player);
        else if(input == 5)
            addToGrid(1,1, player);
        else if(input == 6)
            addToGrid(1,2, player);
        else if(input == 7)
            addToGrid(0,0, player);
        else if(input == 8)
            addToGrid(0,1, player);
        else if(input == 9)
            addToGrid(0,2, player);
    }
      void addToGrid(int i,int j, char player){
        grid[i][j] = player;
    }
    void addToPlace(int p){
        place[p-1] = 1;
    }
     boolean checkPosition(int p){
        if(place[p-1] == 1){
           // System.out.println("Invalid Input enter another number");
            return false;
        }
        else
            return true;
    }
   boolean checkComplete(){
        boolean complete = true;
      
        
        if((grid[0][0]==grid[1][0]) && (grid[0][0]==grid[2][0]) && grid[0][0] !='\u0000'){
            winner=grid[0][0];
            return true;
        }
        else if((grid[0][1]==grid[1][1]) && (grid[0][1]==grid[2][1]) && grid[0][1] !='\u0000'){
            winner=grid[0][1];
            return true;
        }
        else if((grid[0][2]==grid[1][2]) && (grid[0][2]==grid[2][2]) && grid[0][2] !='\u0000'){
           winner=grid[0][2];
            return true;
        }
        else if((grid[0][0]==grid[0][1]) && (grid[0][0]==grid[0][2]) && grid[0][0] !='\u0000'){
           winner=grid[0][0];
            return true;
        }
        else if((grid[1][0]==grid[1][1]) && (grid[1][0]==grid[1][2]) && grid[1][0] !='\u0000'){
            winner=grid[1][0];
            return true;
        }
        else if((grid[2][0]==grid[2][1]) && (grid[2][0]==grid[2][2]) && grid[2][0] !='\u0000'){
            winner=grid[2][0];
            return true;
        }
        else if((grid[0][0]==grid[1][1]) && (grid[0][0]==grid[2][2]) && grid[0][0] !='\u0000'){
           winner=grid[0][0];
            return true;
        }
        else if((grid[2][0]==grid[1][1]) && (grid[2][0]==grid[0][2]) && grid[2][0] !='\u0000'){
           winner=grid[2][0];
            return true;
        }
        else{
            for(int i=0;i<place.length;i++)
                if(place[i] == 0)
                    complete = false;
            if(complete)
                winner = 'd';
        }
        return complete;
    }
   
    
}
