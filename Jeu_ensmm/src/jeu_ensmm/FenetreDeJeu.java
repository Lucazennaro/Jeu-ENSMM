package jeu_ensmm;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Exemple de fenetre de jeu en utilisant uniquement des commandes
 *
 * @author guillaume.laurent
 */
public class FenetreDeJeu extends JFrame implements ActionListener{

    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu1;
    private Timer timer;

    public FenetreDeJeu() {
        // initialisation de la fenetre
        this.setSize(1762, 992);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(1762, 992));
        this.setContentPane(this.jLabel1);
        
        this.pack();
        
        //Creation du jeu
        this.jeu1 = new Jeu ();
        

        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics();
        
        //Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(40, this);
        this.timer.start();
    }

    public Graphics2D getContexte() {
        return contexte;
    }
    public void actionPerformed(ActionEvent e){
        this.jeu1.miseAJour();
        this.jeu1.rendu(contexte);
        this.jLabel1.repaint();
        
    }
    
    public void keyTyped(KeyEvent e) {
        // NOP
    }
    
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu1.getJoueur().setDroite(true);
            
;
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu1.getJoueur().setGauche(true);
        }
        if (evt.getKeyCode() == evt.VK_UP) {
            this.jeu1.getJoueur().setBas(true);
        }
        if (evt.getKeyCode() == evt.VK_DOWN) {
            this.jeu1.getJoueur().setHaut(true);
        }
    }

    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu1.getJoueur().setDroite(false);
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu1.getJoueur().setGauche(false);
        }
        if (evt.getKeyCode() == evt.VK_UP) {
            this.jeu1.getJoueur().setBas(false);
        }
        if (evt.getKeyCode() == evt.VK_DOWN) {
            this.jeu1.getJoueur().setHaut(false);
        }
    }
    

    public static void main(String[] args) {
        FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);
        fenetre.getJeu().rendu(fenetre.getContexte());
    }

    public Jeu getJeu() {
        return jeu1;
    }
    public void tostring(){
        System.out.println(jeu1.getJoueur().toString());
    }


}
