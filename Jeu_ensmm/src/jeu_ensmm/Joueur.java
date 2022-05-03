package jeu_ensmm;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nbouvere
 */
public class Joueur extends Objet {
    private boolean saut;
    private BufferedImage sprite;

    public Joueur(boolean saut, String nom, int x, int y, int largeur, int hauteur, boolean gauche, boolean droite, boolean haut, boolean bas, int vitesse, int score){//, BufferedImage sprite) {
        super(nom, x, y, largeur, hauteur, gauche, droite, haut, bas, vitesse, score);//, sprite);
        this.saut = saut;
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/banane.png"));
        } catch (IOException ex) {
            Logger.getLogger(Personnage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    
    public void setSaut(boolean saut) {
        this.saut = saut;
    }

    public boolean isSaut() {
        return saut;
    }

    public void misAjourScore(Objet objet){
        if( super.collision(objet)==true ){
            super.setScore(super.getScore()+objet.getScore() );
        }
    }
    
 
}
