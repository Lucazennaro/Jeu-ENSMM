package jeu_ensmm;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer; 
import outils.OutilsJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Exemple de fenetre de jeu en utilisant uniquement des commandes
 *
 * @author guillaume.laurent
 */
public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener{

    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu1;
    private Timer timer;

    public FenetreDeJeu(String nom) {
        // initialisation de la fenetre
        this.setSize(1760, 992);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(1760, 992));
        this.setContentPane(this.jLabel1);
        this.addKeyListener(this);
        this.pack();
        //Creation du jeu
        this.jeu1 = new Jeu();
      
       this.jeu1.creationMonJoueur(nom);
       //System.out.println("nom = " + this.jeu1.getJoueur().getNom() + "    id = " + this.jeu1.getJoueur().getId());
       this.jeu1.addJoueurTable();
       this.jeu1.addJoueursListe();
       System.out.println(this.jeu1.getListe());
        
        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics(); 
        
        //Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(40, this);
        this.timer.start();
        
        // ESSAIS BASE DE DONNEES
        
        
        
        System.out.println("nombre de joueurs = " + this.jeu1.nombreDeJoueurs() +"." + "nombre d'objets = " + this.jeu1.nombreObjets());
        //this.jeu1.creationObjet(1, "AH", 20, 10, 600, 1);
   }
    public Graphics2D getContexte() {
        return this.contexte;
    }
    
    public void actionPerformed(ActionEvent e){
        this.jeu1.miseAJour();
        this.jeu1.rendu(contexte);
        this.jLabel1.repaint();
//        System.out.println(jeu1.get.plateforme.getPlateforme()[objet.getX()%31][objet.getY()%31]);
//System.out.println(jeu1.getListe().get(0));
    }
    
    public void keyTyped(KeyEvent e) {
        // NOP
    }
    
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_RIGHT) {
//            System.out.println("droite");
            this.jeu1.getJoueur().setDroite(true) ;
            //System.out.println((int) this.jeu1.getJoueur().getVitesse() + "vitesse ");
           // System.out.println((int) this.jeu1.getListe().get(1).getX()/32);
//            System.out.println(this.jeu1.getPlateforme().getPlateforme()[(int) this.jeu1.getListe().get(0).getX()/32][(int) this.jeu1.getListe().get(0).getY()/32]);
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu1.getJoueur().setGauche(true);
        }
        if (evt.getKeyCode() == evt.VK_DOWN) {
            this.jeu1.getJoueur().setBas(true);
        }
        if (evt.getKeyCode() == evt.VK_UP) {
            this.jeu1.getJoueur().setHaut(true);     
        }
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.dispose();
            this.jeu1.supprimeMonJoueur();
        }
    System.out.println("  x= "+this.jeu1.getJoueur().getX()+"  y=  "+this.jeu1.getJoueur().getY());
    }

    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu1.getJoueur().setDroite(false);
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu1.getJoueur().setGauche(false);
        }
        if (evt.getKeyCode() == evt.VK_DOWN) {
            this.jeu1.getJoueur().setBas(false);
//            System.out.println( this.jeu1.getListe().get(0).isHaut());
        }
        if (evt.getKeyCode() == evt.VK_UP) {
            this.jeu1.getJoueur().setHaut(false);
   
        }
    }
//    public void AffichageScore(Joueur joueur1, Graphics2D fenetre_graphique){
//        fenetre_graphique.drawString("Score : " + joueur1.score(), 10, 20);
//        }
    
    public static void main(String[] args) {
        FenetreDeJeu fenetre = new FenetreDeJeu("joueur");
        fenetre.setVisible(true);
        fenetre.getJeu().rendu(fenetre.getContexte()); 
        
    }

    public Jeu getJeu() {
        return jeu1;
    }
    

    

   

}
