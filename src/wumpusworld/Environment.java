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

    private int numberHoles;

    private int numberGolds;

    private int n;

    private byte maskAgent;
    
    private byte maskWumpus;

    private byte maskGold;

    private byte maskHole;

    private byte maskStink;

    private byte maskBreeze;

    private byte maskLight;

    Random random;

    public Environment(int n) {
        this.init(n);

        this.setWumpus();
        
        this.setHoles(n / 2);
        
        this.setGolds(n / 2);
    }       
    
    public void init(int n) {
        this.n = n;
        
        this.board = new byte[n][n];
        
        this.random = new Random();
        
        this.inicializeBoard();        

        this.maskAgent = (byte) 1;

        this.maskWumpus = (byte) 2;

        this.maskHole = (byte) 4;

        this.maskGold = (byte) 8;

        this.maskStink = (byte) 16;

        this.maskBreeze = (byte) 32;

        this.maskLight = (byte) 64;
    }
    

    public void inicializeBoard() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {                
                this.board[i][j] = 0;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {

                System.out.printf("%4d", this.board[i][j]);
            }
            System.out.println();
        }
    }

    public int generateNumberRandom(int min, int max) {        
        int number = random.nextInt(this.n);
        if (number < min) {
            return min;
        } else if (number > max) {
            return max;
        }
        return number;
    }

    public void setWumpus() {
        int line = generateNumberRandom(0, this.n - 1);
        int column = generateNumberRandom(0, this.n - 1);
        if (line == 0 && column == 0) {
            line = this.n - 1;
            column = this.n - 1;
        }
        this.wumpus = new Wumpus(line, column);
        this.board[line][column] = (byte) (this.board[line][column] | 2);
        this.setStink(line, column);
    }

    public void setHoles(int nHoles) {
        this.holes = new Hole[nHoles];
        for (int i = 0; i < this.holes.length; i++) {
            int line, column;
            while (true) {
                line = generateNumberRandom(0, this.n - 1);
                column = generateNumberRandom(0, this.n - 1);
                if (((byte) this.board[line][column] & this.maskWumpus) == 0 && ((byte) this.board[line][column] & this.maskHole) == 0 && (line != 0 || column != 0)) {
                    break;
                }
            }
            this.holes[i] = new Hole(line, column);
            this.board[line][column] = (byte) (this.board[line][column] | 4);
            this.setBreeze(line, column);
        }
    }

    public void setGolds(int nGolds) {
        this.golds = new Gold[nGolds];
        for (int i = 0; i < this.golds.length; i++) {
            int line, column;

            while (true) {
                line = generateNumberRandom(0, this.n - 1);
                column = generateNumberRandom(0, this.n - 1);
                if (((byte) this.board[line][column] & this.maskWumpus) == 0 && ((byte) this.board[line][column] & this.maskHole) == 0 && ((byte) this.board[line][column] & this.maskGold) == 0 && (line != 0 || column != 0)) {
                    break;
                }
            }
            this.golds[i] = new Gold(line, column);
            this.board[line][column] = (byte) (this.board[line][column] | this.maskGold);
            this.setLigth(line, column);
        }
    }

    public void setStink(int line, int column) {
        if (line - 1 >= 0) {
            this.board[line - 1][column] = (byte) (this.board[line - 1][column] | this.maskStink);
        }
        if (line + 1 < this.n) {
            this.board[line + 1][column] = (byte) (this.board[line + 1][column] | this.maskStink);
        }
        if (column - 1 >= 0) {
            this.board[line][column - 1] = (byte) (this.board[line][column - 1] | this.maskStink);
        }
        if (column + 1 < this.n) {
            this.board[line][column + 1] = (byte) (this.board[line][column + 1] | this.maskStink);
        }
    }

    public void setBreeze(int line, int column) {
        if (line - 1 >= 0) {
            this.board[line - 1][column] = (byte) (this.board[line - 1][column] | this.maskBreeze);
        }
        if (line + 1 < this.n) {
            this.board[line + 1][column] = (byte) (this.board[line + 1][column] | this.maskBreeze);
        }
        if (column - 1 >= 0) {
            this.board[line][column - 1] = (byte) (this.board[line][column - 1] | this.maskBreeze);
        }
        if (column + 1 < this.n) {
            this.board[line][column + 1] = (byte) (this.board[line][column + 1] | this.maskBreeze);
        }
    }

    public void setLigth(int line, int column) {

        if (line - 1 >= 0) {
            this.board[line - 1][column] = (byte) (this.board[line - 1][column] | this.maskLight);
        }
        if (line + 1 < this.n) {
            this.board[line + 1][column] = (byte) (this.board[line + 1][column] | this.maskLight);
        }
        if (column - 1 >= 0) {
            this.board[line][column - 1] = (byte) (this.board[line][column - 1] | this.maskLight);
        }
        if (column + 1 < this.n) {
            this.board[line][column + 1] = (byte) (this.board[line][column + 1] | this.maskLight);
        }
    }
    
    public byte getSensation(int i, int j){
        return this.board[i][j];
    }
}
