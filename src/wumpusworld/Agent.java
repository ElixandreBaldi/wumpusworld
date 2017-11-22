/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.lang.reflect.Array;
import java.util.Arrays;
import static wumpusworld.WumpusWorld.MASKUNKNOWN;
import static wumpusworld.WumpusWorld.random;

/**
 *
 * @author administrador
 */
public class Agent {
    private int line;
    
    private int column;
    
    private int n;
    
    private byte environment[][];
    
    private boolean DANGER;
    
    private boolean LIGHT;
    
    private int iMovementLight;
    
    Agent(int line, int column, int n) {        
        this.DANGER = false;        
        this.LIGHT = false;
        this.iMovementLight = 1;
        this.n = n;
        this.line = line;
        this.column = column;
        this.environment = new byte[n][n];
        this.inicializeEnvironment();        
    }
    
    public void inicializeEnvironment() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {                
                this.environment[i][j] = MASKUNKNOWN;
            }
        }
        this.environment[0][0] = 0;
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
    
    public boolean[] searchSafePosition() {
        boolean status[] = new boolean[4]; // top, right, bottom, left
        Arrays.fill(status, Boolean.FALSE);
        
        if(this.line > 0) {
            if( (this.environment[this.line - 1][this.column] & MASKUNKNOWN) == 0 )
                status[0] = true;
        }
        if(this.column < this.n - 1) {
            if ( (this.environment[this.line][this.column + 1] & MASKUNKNOWN) == 0 )
            status[1] = true;
        }
        if(this.line < this.n - 1) {
            if( (this.environment[this.line + 1][this.column] & MASKUNKNOWN) == 0 )
                status[2] = true;
        }
        if(this.column > 0) {
            if( (this.environment[this.line][this.column - 1] & MASKUNKNOWN) == 0 )
                status[3] = true;        
        }
        
        return status;        
    }
    
    public void action() {
        boolean positions[] = this.searchSafePosition();  
        System.out.println("Position: "+this.line+ ", "+this.column);
        
        if (this.LIGHT){
            System.out.println("Detect Light");
            if(this.iMovementLight == 1) {
                if(moveTop())
                    this.iMovementLight *= -1;
                else
                    this.iMovementLight++;
            }else if(this.iMovementLight == -1) {
                this.iMovementLight *= -1;
                this.iMovementLight++;
                moveBottom();
            }else if(this.iMovementLight == 2) {
                if(moveRight())
                    this.iMovementLight *= -1;
                else
                    this.iMovementLight++;
            }else if(this.iMovementLight == -2) {
                this.iMovementLight *= -1;
                this.iMovementLight++;
                moveLeft();
            }else if(this.iMovementLight == 3) {
                if(moveBottom())
                    this.iMovementLight *= -1;
                else
                    this.iMovementLight++;
            }else if(this.iMovementLight == -3) {
                this.iMovementLight *= -1;
                this.iMovementLight++;
                moveTop();
            }else if(this.iMovementLight == 4){
                if(moveLeft())
                    this.iMovementLight *= -1;
                else
                    this.iMovementLight++;
            }else if(this.iMovementLight == -4) {
                this.iMovementLight *= -1;
                this.iMovementLight++;
                moveRight();
            }else{
                System.out.println("Agent - Line 164");
                System.exit(1);
            }
        }else if(this.DANGER) {                        
            System.out.println("Detect Danger");
            this.movementSafePosition(positions);
        }else {   
            System.out.println("Detect Nothing");
            this.movementRondonUnknown(positions);
        }        
    }   
        
    public void movementRondonUnknown(boolean[] positions) {    
        if(!positions[0] && moveTop())
            return;
        else if(!positions[1] && moveRight())
            return;
        else if(!positions[2] && moveBottom())
            return;
        else if(!positions[3] && moveLeft())
            return;
        else
            this.movementRondom();            
        
    }
    
    public void movementRondom(){            
        while(true) {
            int i = random.nextInt(4);
            if(i == 0 && this.moveTop()) {
                return;
            } else if(i == 1 && this.moveRight()){
                return;
            } else if(i == 2 && this.moveBottom()){
                return;
            } else if(i == 3 && this.moveLeft()){
                return;
            }
        }                
    }
    
    public void movementSafePosition(boolean[] positions) {                
        int i = this.randomMovement(positions);
        
        if(i == 0)        
            moveTop();
        else if(i == 1)
            moveRight();
        else if(i == 2)
            moveBottom();
        else if(i == 3)
            moveLeft();
        else {
            System.out.println("Agent - Line 229");
            System.exit(1);
        }
        
        this.DANGER = false;
    }
    
    public int randomMovement(boolean positions[]) {        
        while(true) {
            int i = random.nextInt(4);
            if(positions[i])
                return i;
        }
    }
    
    void setDanger() {
        this.DANGER = true;
    }
    
    void setLight() {
        this.LIGHT = true;
    }
}
