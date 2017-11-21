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
public class Agent {
    private int line;
    
    private int column;
    
    private int n;
    
    private byte environment[][];
    
    Agent(int line, int column, int n) {
        this.n = n;
        this.line = line;
        this.column = column;
        this.environment = new byte[n][n];
        this.inicializeEnvironment();        
    }
    
    public void inicializeEnvironment() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {                
                this.environment[i][j] = 0;
            }
        }
    }
    
    public boolean moveTop() {
        if(line > 0)
            this.line--;
        else
            return false;
        
        return true;
    }
    
    public boolean moveBottom() {
        if(line < this.n - 1)
            this.line++;
        else
            return false;
        
        return true;
    }
    
    public boolean moveLeft() {
        if(column > 0)
            this.column--;
        else
            return false;
        
        return true;
    }
    
    public boolean moveRight() {
        if(column < this.n - 1)
            this.column++;
        else
            return false;
                
        return true;
    }
    
    public void action() {
        if(line % 2 == 0) {
            if(!this.moveRight()) {
                this.moveBottom();
            }
        }
        else {
            if(!this.moveLeft())
                this.moveBottom();
        }
    }
    
    public int getLine() {        
        return this.line;
    }
    
    public int getColumn() {        
        return this.column;
    }
    
    public void setKnowledge(byte know) {
        this.environment[line][column] = know;
    }
    
    public void printEnvironment() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {

                System.out.printf("%4d", this.environment[i][j]);
            }
            System.out.println();
        }
    }
}
