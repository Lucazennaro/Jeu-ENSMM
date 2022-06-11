package jeu_ensmm;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import outils.OutilsJDBC;
import java.util.Random;


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
//    private BufferedImage sprite;

    public Joueur(int id, boolean saut, String nom, int x, int y, boolean gauche, boolean droite, boolean haut, boolean bas, int vitesse, int score, int numSprite){//, BufferedImage sprite) {
        super(id, nom, x, y, gauche, droite, haut, bas, vitesse, score, numSprite);
        this.saut = saut;
//        try {
//            this.setSprite(ImageIO.read(getClass().getResource("../resources/donkeyKong.png")));
//        } catch (IOException ex) {
//            Logger.getLogger(Personnage.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
   
    public void setSaut(boolean saut) {
        this.saut = saut;
    }

    public boolean isSaut() {
        return saut;
    }

    @Override
    public String toString() {
        return "Joueur{" + "saut=" + saut + '}';
    }


//    public void misAjourScore(Objet objet){
//        if( super.collision(objet)==true ){
//            super.setScore(super.getScore()+objet.getScore() );
//            objet.setX(Random().nextInt(1776-objet.getLargeur()));
//            objet.set(Random().nextInt(992-objet.getHauteur()));
//        }
//    }

    

    public void misAjourScore(Objet objet){
        if( super.collision(objet)==true ){
            super.setScore(super.getScore()+objet.getScore() );
            Random newPosX = new Random();
            Random newPosY = new Random();
            objet.setX(newPosX.nextInt(1776-objet.getLargeur()));
            objet.setY(newPosY.nextInt(992-objet.getHauteur()));
        }
    }

   

    Object Getscore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}


