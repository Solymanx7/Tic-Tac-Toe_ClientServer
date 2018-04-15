
package tic.tac.toe.client;

import java.io.IOException;
import java.util.Scanner;


public class Main_Client {
    
    public static void main(String[] args)
    {
        String ip = "localhost";
        int port = 9999;
        TicTacToeClient client = new TicTacToeClient(ip, port);
        client.Play();
        
        
    }
    
}
