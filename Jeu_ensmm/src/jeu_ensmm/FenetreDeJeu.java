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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exemple de fenetre de jeu en utilisant uniquement des commandes
 *
 * @author guillaume.laurent
 */
public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener {
   
    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu1;
    private Timer timer;
    
    public FenetreDeJeu(String nom) throws SQLException {
        System.out.println("départ");
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
        System.out.println("lancement jeu");
        this.jeu1.openConnexion();
        System.out.println("lancement connexion");
        this.jeu1.creationMonJoueur(nom);
        this.jeu1.addJoueurTable();
        this.jeu1.addJoueursListe();
        System.out.println("attention"+this.jeu1.getListe());
        
        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics(); 
        
        //Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(40, this);
        this.timer.start();
        
        System.out.println("nombre de joueurs = " + this.jeu1.nombreDeJoueurs() +"." + "nombre d'objets = " + this.jeu1.nombreObjets());
   }
    public Graphics2D getContexte() {
        return this.contexte;
    }
    
    public void actionPerformed(ActionEvent e ) {
        this.jeu1.miseAJour();
        this.jeu1.rendu(contexte);
        this.jLabel1.repaint();                 
        this.jeu1.miseAJourDataBase();
        this.jeu1.rendu(contexte); 
        if (this.jeu1.finDuJeu()){
            this.jeu1.affichageClassement(this.jeu1.classementJoueurs());
            this.timer.stop();
            this.jeu1.videTable("joueur");
            this.dispose();
        }   
    }
    
    public void keyTyped(KeyEvent e) {
        // NOP;
    }
    
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu1.getJoueur().setDroite(true) ;
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu1.getJoueur().setGauche(true);
        }
        if (evt.getKeyCode() == evt.VK_UP) {

            this.jeu1.getJoueur().setHaut(true);                      
        }    

            this.jeu1.getJoueur().setHaut(true);     
        
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            this.dispose();
            this.timer.stop();
            this.jeu1.supprimeMonJoueur();
            try {
                this.jeu1.closeConnexion() ;
            } catch (SQLException ex) {
                Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("fermeture connexion");
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
        }
        if (evt.getKeyCode() == evt.VK_UP) {
            this.jeu1.getJoueur().setHaut(false);
        }

    }

    
    public static void main(String[] args) throws SQLException  {
        FenetreDeJeu fenetre = new FenetreDeJeu("joueur");
        fenetre.setVisible(true);
        fenetre.getJeu().rendu(fenetre.getContexte()); 
        
    }

    public Jeu getJeu() {
        return jeu1;
    }
}
