/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static wumpusworld.Interface.MASKBREEZE;
import static wumpusworld.Interface.MASKGOLD;
import static wumpusworld.Interface.MASKHOLE;
import static wumpusworld.Interface.MASKLIGHT;
import static wumpusworld.Interface.MASKSTINK;
import static wumpusworld.Interface.MASKWUMPUS;
import static wumpusworld.Interface.a;
import static wumpusworld.Interface.t;

/**
 *
 * @author root
 */
public class Interface extends javax.swing.JFrame {

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

    byte buffer_env[][];
    //int n;
    //Environment t;
    JPanel AreaDesenho;
    Button Start;
    Dimension window_size;
    int tam_casa;
    boolean flag, visao;

    BufferedImage Ouro, Brilho, Wumpus, Fedor, Buraco, Brisa, Livre, Agente;

    public Interface() throws IOException {
        initComponents();
        this.n = 10;
        this.tam_casa = 50;
        buffer_env = null;
        visao = true;
        Start = new Button();
        Start.setLabel("Play");
        Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t = new Environment(Interface.n);
                a = new Agent(0, 0, Interface.n);
                flag = false;

                while (true) {
                    a.action();
                    a.setKnowledge(t.getSensation(a.getLine(), a.getColumn()));
                    verifyFuture(a.getLine(), a.getColumn());
                    printMatrix();

                    if (flag) {
                        break;
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        Agente = ImageIO.read(new File("/home/matheus/Documents/IA/securefolder/wumpusworld/src/wumpusworld/Agente.png"));
//        Livre = ImageIO.read(new File("/home/matheus/Documents/IA/securefolder/wumpusworld/src/wumpusworld/Livre.png"));
        Ouro = ImageIO.read(new File("/home/matheus/Documents/IA/securefolder/wumpusworld/src/wumpusworld/Ouro.png"));
        Brilho = ImageIO.read(new File("/home/matheus/Documents/IA/securefolder/wumpusworld/src/wumpusworld/Brilho.png"));
        Buraco = ImageIO.read(new File("/home/matheus/Documents/IA/securefolder/wumpusworld/src/wumpusworld/Buraco.png"));
        Brisa = ImageIO.read(new File("/home/matheus/Documents/IA/securefolder/wumpusworld/src/wumpusworld/Brisa.png"));
        Wumpus = ImageIO.read(new File("/home/matheus/Documents/IA/securefolder/wumpusworld/src/wumpusworld/Wumpus.png"));
        Fedor = ImageIO.read(new File("/home/matheus/Documents/IA/securefolder/wumpusworld/src/wumpusworld/Fedor.png"));

        AreaDesenho = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.lightGray);
                int size = tam_casa, posic_inicial = (window_size.width / 2) - ((n * size) / 2);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        g.fillRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                    }
                }

                g.setColor(Color.green);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        g.drawRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                    }
                }

                if (buffer_env != null) {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            System.out.printf("%4d", buffer_env[i][j]);
                            

                            if (buffer_env[i][j] == 0) {
                                g.setColor(Color.darkGray);
                                g.fillRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.setColor(Color.green);
                                g.drawRect(posic_inicial + (j * size), 60 + (i * size), size, size);
//                                g.drawImage(Livre, posic_inicial + (j * size), 60 + (i * size), null);
                            } else if ((buffer_env[i][j] & (MASKWUMPUS)) == 2) {
                                g.setColor(Color.darkGray);
                                g.fillRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.setColor(Color.green);
                                g.drawRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.drawImage(Wumpus, posic_inicial + (j * size), 60 + (i * size), null);
                            } else if ((buffer_env[i][j] & (MASKHOLE)) == 4) {
                                g.setColor(Color.darkGray);
                                g.fillRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.setColor(Color.green);
                                g.drawRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.drawImage(Buraco, posic_inicial + (j * size), 60 + (i * size), null);
                            } else if ((buffer_env[i][j] & (MASKGOLD)) == 8) {
                                g.setColor(Color.darkGray);
                                g.fillRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.setColor(Color.green);
                                g.drawRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.drawImage(Ouro, posic_inicial + (j * size), 60 + (i * size), null);
                            } else if ((buffer_env[i][j] & (MASKSTINK)) == 16) {
                                g.setColor(Color.darkGray);
                                g.fillRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.setColor(Color.green);
                                g.drawRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.drawImage(Fedor, posic_inicial + (j * size), 60 + (i * size), null);
                            } else if ((buffer_env[i][j] & (MASKBREEZE)) == 32) {
                                g.setColor(Color.darkGray);
                                g.fillRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.setColor(Color.green);
                                g.drawRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.drawImage(Brisa, posic_inicial + (j * size), 60 + (i * size), null);
                            } else if ((buffer_env[i][j] & (MASKLIGHT)) == 64) {
                                g.setColor(Color.darkGray);
                                g.fillRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.setColor(Color.green);
                                g.drawRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.drawImage(Brilho, posic_inicial + (j * size), 60 + (i * size), null);
                            }

                            if (i == a.getLine() && j == a.getColumn()) {
                                g.setColor(Color.darkGray);
                                g.fillRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.setColor(Color.green);
                                g.drawRect(posic_inicial + (j * size), 60 + (i * size), size, size);
                                g.drawImage(Agente, posic_inicial + (j * size), 60 + (i * size), null);
                            }
                        }
                        System.out.println();
                    }
                }
            }
        };

        this.add(Start);
        this.add(AreaDesenho);
        initialize();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        menuSize = new javax.swing.JMenuItem();
        menuVisao = new javax.swing.JMenuItem();
        menuSair = new javax.swing.JMenuItem();
        menuSobre = new javax.swing.JMenu();
        menuInfo = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mundo do Wumpus");
        setSize(new java.awt.Dimension(300, 300));

        menuArquivo.setText("Arquivo");

        menuSize.setText("Definir Tamanho Tabuleiro");
        menuSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSizeActionPerformed(evt);
            }
        });
        menuArquivo.add(menuSize);

        menuVisao.setText("Visao Agente/Visao Geral");
        menuVisao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuVisaoActionPerformed(evt);
            }
        });
        menuArquivo.add(menuVisao);

        menuSair.setText("Sair");
        menuSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSairActionPerformed(evt);
            }
        });
        menuArquivo.add(menuSair);

        jMenuBar1.add(menuArquivo);

        menuSobre.setText("Sobre");

        menuInfo.setText("Info");
        menuInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuInfoActionPerformed(evt);
            }
        });
        menuSobre.add(menuInfo);

        jMenuBar1.add(menuSobre);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSairActionPerformed
        System.exit(1);
    }//GEN-LAST:event_menuSairActionPerformed

    private void menuInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuInfoActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "Criado por: Elixandre Baldi, Matheus Leonardo e Nicolas Afonso");
    }//GEN-LAST:event_menuInfoActionPerformed

    private void menuSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSizeActionPerformed
        this.n = Integer.parseInt(JOptionPane.showInputDialog("Informe a dimensao do tabuleiro:"));
        initialize();
    }//GEN-LAST:event_menuSizeActionPerformed

    private void menuVisaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVisaoActionPerformed
        visao = !visao;
    }//GEN-LAST:event_menuVisaoActionPerformed

    public void initialize() {
        t = new Environment(n);
        window_size = new Dimension((n * tam_casa) + (tam_casa * 4), (n * tam_casa) + (tam_casa * 4));
        this.setSize(window_size);
        AreaDesenho.setSize(window_size);
        AreaDesenho.setVisible(true);
        AreaDesenho.repaint();

        Start.setSize(new Dimension(tam_casa, tam_casa));
        Start.setVisible(true);
        Start.setLocation((window_size.width / 2) - (tam_casa / 2), 7);
    }

    public void printMatrix() {
        System.out.println("");
        if (visao) {
            buffer_env = a.printEnvironment();
        } else {
            buffer_env = t.printBoard();
        }

//        for (int i = 0; i < this.n; i++) {
//            for (int j = 0; j < this.n; j++) {
//                System.out.printf("%4d", buffer_env[i][j]);
//            }
//            System.out.println();
//        }
        System.out.println("");
        System.out.println("");
        AreaDesenho.paint(AreaDesenho.getGraphics());
    }

    public void verifyFuture(int line, int column) {
        byte teste = 8;
        byte sensation = t.getSensation(line, column);
        if ((sensation & MASKWUMPUS) != 0 || (sensation & MASKHOLE) != 0) { // morreu
            System.out.println("You lost");
//            printMatrix();
//            System.exit(0);
            flag = true;
            boolean temp = visao;
            visao = false;
            printMatrix();
            JOptionPane.showMessageDialog(this, "Voce Perdeu!");
            visao = temp;
        } else if ((sensation & MASKGOLD) != 0) { // ganhou
//            System.out.println("Congratilations! You Winn! "+a.contMoviment +" movimentos!");
//            printMatrix();
//            System.exit(0);
            flag = true;
            boolean temp = visao;
            visao = false;
            printMatrix();
            JOptionPane.showMessageDialog(this, "Voce Venceu!\n"+a.contMoviment +" movimentos!");
            visao = temp;
        } else if ((sensation & MASKBREEZE) != 0 || (sensation & MASKSTINK) != 0) {
            a.setDanger();
        } else if ((sensation & MASKLIGHT) == MASKLIGHT) {
            a.setLight();
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Interface().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenuItem menuInfo;
    private javax.swing.JMenuItem menuSair;
    private javax.swing.JMenuItem menuSize;
    private javax.swing.JMenu menuSobre;
    private javax.swing.JMenuItem menuVisao;
    // End of variables declaration//GEN-END:variables
}
