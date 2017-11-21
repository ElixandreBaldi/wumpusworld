/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

/**
 *
 * @author administrador
 */
public class WumpusWorld {
    public static int n = 15;
    public static Agent a;
    public static Environment t;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        t = new Environment(WumpusWorld.n);
        a = new Agent(0, 0, WumpusWorld.n);
        
        while(true) {
            a.action();             
            WumpusWorld.verifyNewPosition( t.getSensation( a.getLine(), a.getColumn()) );
            
            if(a.getLine() == 14 &&  a.getColumn() == 14){
                System.out.println("");
                a.printEnvironment();
                System.out.println("");
                System.out.println("");
                t.printBoard();
                break;
            }
                
        }
    }
    
    public static void verifyNewPosition(byte sensation) {
        a.setKnowledge(sensation);
    }
}
