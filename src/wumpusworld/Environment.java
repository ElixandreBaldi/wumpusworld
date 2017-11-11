/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author administrador
 */
public class Environment {
    private byte board[][];
    
    private int indexGolds[][];
    
    private Hole holes[];    
    
    private Breeze breezes[];    
    
    private Stink stinks[];    
    
    private Gold golds[];    
    
    private Light lights[];    
    
    private Wumpus wumpus;
    
    private Agent agent;
    
    private Exit exit;    
    
    private int numberHoles;
    
    private int numberGolds;
    
    private int n;        
    
    private byte maskWumpus;
    
    private byte maskGold;
    
    private byte maskHole;
    
    private byte maskStink;
    
    Random random;
    
    public Environment(int n) {        
        this.n = n;
        this.board = new byte[n][n];                
        this.random = new Random();
        this.inicializeBoard();
        
        this.maskWumpus = (byte) 0;
        this.maskWumpus = (byte) (this.maskWumpus | (1 << 1));
            
        this.maskHole = (byte) 0;
        this.maskHole = (byte) (this.maskHole | (2 << 1));
            
        this.maskGold = (byte) 0;
        this.maskGold = (byte) (this.maskGold | (4 << 1));                
        
        this.maskStink = (byte) 0;
        this.maskStink = (byte) (this.maskStink | (4 << 1));    
        
        
        this.setWumpus();
        this.setHoles(n/2);
        this.setGolds(n/2);
        
        this.setStink();
        this.setBreeze();
        this.setLigth();
        this.setExit();
        printBoard();
    }
    
    public void inicializeBoard() {
        for(int i = 0; i < this.n; i++) {
            for(int j = 0; j < this.n; j++) {
                byte newIndex = 0;                
                this.board[i][j] = newIndex;                   
            }            
        }
    }
    
    public void printBoard() {
        for(int i = 0; i < this.n; i++) {
            for(int j = 0; j < this.n; j++) {
                System.out.print(this.board[i][j] + "   ");                   
            }            
            System.out.println();
        }        
    }    
    
    public int generateNumberRandom(int min, int max){         
        //int number =random.nextInt(max - min + 1) + min;
        int number = random.nextInt(this.n);           
        if(number < min) {            
            return min;
        } else if(number > max) {
            return max;
        }        
        return number;            
    }
    
    public void setWumpus() {
        int line = generateNumberRandom(0, this.n - 1);
        int column = generateNumberRandom(0, this.n - 1);
        
        if(line == 0 && column == 0) {
            line = this.n - 1;
            column = this.n - 1;           
        }
        
        this.wumpus = new Wumpus(line, column);
        this.board[line][column] = (byte) (this.board[line][column] | (1 << 1));
    }
    
    public void setHoles(int nHoles) {
        this.holes = new Hole[nHoles];
        for(int i = 0; i < this.holes.length; i++) {
            int line, column;
            while(true) {                
                line = generateNumberRandom(0, this.n - 1);
                column = generateNumberRandom(0, this.n - 1);
                if( ( (byte) this.board[line][column] & this.maskWumpus ) == 0 && ( (byte) this.board[line][column] & this.maskHole ) == 0 && (line !=0 || column != 0))
                    break;
            }                        
            
            this.holes[i] = new Hole(line, column);            
            this.board[line][column] = (byte) (this.board[line][column] | (2 << 1));
            System.out.println(this.holes[i].getLine() + ", " +this.holes[i].getColumn());
        }            
    }
    
    public void setGolds(int nGolds) {
        this.golds = new Gold[nGolds];
        for(int i = 0; i < this.golds.length; i++) {
            int line, column;
        
            while(true) {
                line = generateNumberRandom(0, this.n - 1);
                column = generateNumberRandom(0, this.n - 1);
                if( ( (byte) this.board[line][column] & this.maskWumpus ) == 0 && ( (byte) this.board[line][column] & this.maskHole ) == 0 && ( (byte) this.board[line][column] & this.maskGold ) == 0 && (line !=0 || column != 0))
                    break;
            }
            this.golds[i] = new Gold(line, column);
            this.board[line][column] = (byte) (this.board[line][column] | (4 << 1));
        } 
    }
    
    public void setStink() {
        int line = this.wumpus.getLine();
        int column = this.wumpus.getColumn();
        
        this.board[line - 1][column] = (byte) (this.board[line - 1][column] | this.maskStink);
        this.board[line + 1][column] = (byte) (this.board[line + 1][column] | this.maskStink);
        this.board[line][column - 1] = (byte) (this.board[line][column - 1] | this.maskStink);
        this.board[line][column + 1] = (byte) (this.board[line][column + 1] | this.maskStink);
    }
    public void setBreeze(){}
    public void setLigth(){}
    public void setExit(){}
}