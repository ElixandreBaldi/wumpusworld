/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import static java.lang.Math.random;
import static java.lang.System.exit;
import java.util.Random;

/**
 *
 * @author administrador
 */
public class WumpusWorld {
    public static int n = 15;
    
    public static Agent a;
    
    public static Environment t;
    
    public static final byte MASKAGENT = 1;
    
    public static final byte MASKWUMPUS = 2;    

    public static final byte MASKHOLE = 4;
    
    public static final byte MASKGOLD = 8;

    public static final byte MASKSTINK = 16;

    public static final byte MASKBREEZE = 32;

    public static final byte MASKLIGHT = 64;     
    
    public static final byte MASKUNKNOWN = (byte) 128;
    
    public static Random random = new Random();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        t = new Environment(WumpusWorld.n);
        a = new Agent(0, 0, WumpusWorld.n);
        
        while(true) {
            a.action();             
            a.setKnowledge( t.getSensation( a.getLine(), a.getColumn()) );    
            WumpusWorld.verifyFuture(a.getLine(), a.getColumn());            
            WumpusWorld.printMatrix();                
        }
        
    }   
    public static void printMatrix() {
        System.out.println("");
        a.printEnvironment();
        System.out.println("");
        System.out.println("");
        t.printBoard();        
    }

    private static void verifyFuture(int line, int column) {
        byte teste = 8;
        byte sensation = t.getSensation(line, column);
        if( (sensation & MASKWUMPUS) != 0 || (sensation & MASKHOLE) != 0){ // morreu
            System.out.println("You lost");
            printMatrix();
            System.exit(0);
        } else if( (sensation & MASKGOLD) != 0) { // ganhou
            System.out.println("Congratilations! You Winn!");
            printMatrix();
            System.exit(0);
        } else if( (sensation & MASKBREEZE) != 0 || (sensation & MASKSTINK) != 0){
            a.setDanger();
        } else if( (sensation & MASKLIGHT) == MASKLIGHT)
            a.setLight();
    }
}
